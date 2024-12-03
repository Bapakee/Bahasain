from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from tensorflow.keras.models import load_model
from tensorflow.keras.preprocessing.sequence import pad_sequences
import numpy as np
import pickle
import re
from builtins import max
import csv

app = FastAPI()

# Define a request model for the input data
class InputText(BaseModel):
    text: str   

# Function to get dataset from CSV files
def get_dataset():
    """
    Reads multiple CSV files and returns only the non-empty rows from all files.

    Returns:
        list: A combined list of non-empty rows from all CSV files.
    """
    valid_rows = []

    # List of file paths
    file_paths = ["./dataset/train_preprocess.csv", "./dataset/valid_preprocess.csv", "./dataset/test_preprocess.csv"]

    # Loop through each file and collect non-empty rows
    for file_path in file_paths:
        with open(file_path, 'r') as csv_file:
            reader = csv.reader(csv_file)
            valid_rows.extend(row for row in reader if row)  # Add non-empty rows to valid_rows

    return valid_rows

# Function to calculate the maximum sequence length
def max_len():
    sentences = []
    labels = []
    kalimat = []
    label = []
    current_sentence = []
    current_label = []

    dataset = get_dataset()
    for row in dataset:
        sentences.append(row[0])
        labels.append(row[1]) 

    for i in range(len(sentences)):
        word = sentences[i]
        word_label = labels[i]  # Rename to avoid conflict with the labels list

        # Add word and label to the current sentence
        current_sentence.append(word)
        current_label.append(word_label)

        # If the word is a period, finalize the current sentence and start a new one
        if word == '.':
            kalimat.append(current_sentence)
            label.append(current_label)
            current_sentence = []
            current_label = []

    # Add the last sentence if it was not terminated with a period
    if current_sentence:
        kalimat.append(current_sentence)
        label.append(current_label)

    sequences = tokenizer.texts_to_sequences(kalimat)
    return max(len(seq) for seq in sequences)

label_mapping = {
    'B-NN': 'Noun','I-NN': 'Noun',
    'B-SC': 'Conjunction','I-SC': 'Conjunction',
    'B-VB': 'Verb','I-VB': 'Verb',
    'B-NNP': 'ProperNoun','I-NNP': 'ProperNoun',
    'B-JJ': 'Adjective','I-JJ': 'Adjective',
    'B-RB': 'Adverb','I-RB': 'Adverb',
    'B-IN': 'Preposition','I-IN': 'Preposition',
    'B-Z': 'Punctuation','I-Z': 'Punctuation',
    'B-CD': 'Number','I-CD': 'Number',
    'B-CC': 'CoordConj','I-CC': 'CoordConj',
    'B-PR': 'Pronoun',    'I-PR': 'Pronoun',
    'B-PRP': 'Preposition',    'I-PRP': 'Preposition',
    'B-MD': 'Modal',    'B-FW': 'ForeignWord',
    'B-NEG': 'Negative',    'B-DT': 'Determiner',
    'B-NND': 'DerivedNoun',    'I-NND': 'DerivedNoun',
    'B-SYM': 'Symbol',    'I-SYM': 'Symbol',
    'B-RP': 'Particle',
    'B-WH': 'QuestionWord',    'I-WH': 'QuestionWord',
    'B-OD': 'Ordinal',    'I-OD': 'Ordinal',
    'B-X': 'Unknown',    'I-X': 'Unknown',
    'B-UH': 'Interjection',    'I-UH': 'Interjection'
}

# Load pre-trained model and tokenizer
MODEL_PATH = "model_POS_v3.h5"
TOKENIZER_PATH = "./tokenizer/tokenizer.pickle"
TAG_TOKENIZER_PATH = "./tokenizer/tag_tokenizer.pickle"
TAG_INDEX_PATH = "tag_index.pickle"

try:
    model = load_model(MODEL_PATH)
except Exception as e:
    raise RuntimeError(f"Failed to load the model: {e}")

try:
    with open(TOKENIZER_PATH, "rb") as handle:
        tokenizer = pickle.load(handle)
except Exception as e:
    raise RuntimeError(f"Failed to load the tokenizer: {e}")

try:
    with open(TAG_TOKENIZER_PATH, "rb") as handle:
        tag_tokenizer = pickle.load(handle)
except Exception as e:
    raise RuntimeError(f"Failed to load the tokenizer: {e}")


MAX_LENGTH = max_len()

# Function to predict POS tags
def predict_pos_tags(input_sentence: str):
    test_sentence =  input_sentence.split()

    original_sentence_length = len(test_sentence)
    test_sentence.append("UNK")  # Add trigger

    test_sequence = tokenizer.texts_to_sequences([test_sentence])
    test_sequence_padded = pad_sequences(test_sequence, maxlen=MAX_LENGTH, padding='post')

    predictions = model.predict(test_sequence_padded, verbose=False)
    predicted_tags = np.argmax(predictions, axis=-1)

    tag_index = tag_tokenizer.word_index

    predicted_tags_list = [list(tag_index.keys())
                       [list(tag_index.values()).index(p)] for p in predicted_tags[0] if p != 0]

    predicted_tags_list = [label_mapping[label.upper()] for label in predicted_tags_list]
    predicted_tags_list = predicted_tags_list[:original_sentence_length]

    return predicted_tags_list

@app.post("/predict")
async def get_prediction(input_data: InputText):
    try:
        text = input_data.text

        if not isinstance(text, str) or not text.strip():
            raise HTTPException(status_code=400, detail="Invalid input text. It must be a non-empty string.")

        predicted_tags = predict_pos_tags(text)
        return predicted_tags

    except Exception as e:
        return {'error': str(e)}
