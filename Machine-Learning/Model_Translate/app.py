from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import numpy as np
import pickle
from tensorflow.keras.models import load_model
from tensorflow.keras.preprocessing.sequence import pad_sequences

# Define the FastAPI app
app = FastAPI()

inputs = []
outputs_i = []
outputs = []

# Define the file paths
input_file_path = './dataset/english.txt'  # Source sentences
output_file_path = './dataset/indonesian.txt'  # Target sentences

# Read input sentences
with open(input_file_path, 'r', encoding='utf-8') as input_file:
    for line in input_file:
        inputs.append(line.strip())

# Read target sentences
with open(output_file_path, 'r', encoding='utf-8') as output_file:
    for line in output_file:
        # Add special tokens for intermediate and final outputs
        temp_op = line.strip()
        op_i = '<sos> ' + temp_op  # Add start-of-sequence token
        op = temp_op + ' <eos>'   # Add end-of-sequence token
        outputs_i.append(op_i)
        outputs.append(op)

# Take Max
max_sentences = 9500
inputs = inputs[:max_sentences]
outputs_i = outputs_i[:max_sentences]
outputs = outputs[:max_sentences]

# Load tokenizers
with open('./pickle/input_tokenizer.pickle', 'rb') as handle:
    input_tokenizer = pickle.load(handle)
with open('./pickle/output_tokenizer.pickle', 'rb') as handle:
    output_tokenizer = pickle.load(handle)

# Process tokenized data
inputs_seq = input_tokenizer.texts_to_sequences(inputs)
inputs_word2index = input_tokenizer.word_index
inputs_numwords = len(inputs_word2index) + 1
inputs_maxlen = max(len(s) for s in inputs_seq)

outputs_i_seq = output_tokenizer.texts_to_sequences(outputs_i)
outputs_seq = output_tokenizer.texts_to_sequences(outputs)
outputs_word2index = output_tokenizer.word_index

# Load models
encoder_model = load_model('./models/encoder_model.h5')
decoder_model = load_model('./models/decoder_model.h5')

index_to_word_input = {v: k for k, v in inputs_word2index.items()}
index_to_word_output = {v: k for k, v in outputs_word2index.items()}
outputs_maxlen = 20  # Replace with your actual maximum length

# Input model for FastAPI
class TranslationRequest(BaseModel):
    text: str

# Preprocess input text
def preprocess_input(text):
    tokens = [inputs_word2index.get(word, 0) for word in text.lower().split()]
    padded_seq = pad_sequences([tokens], maxlen=inputs_maxlen, padding='post')
    return padded_seq

# Translate function
def translate(txt):
    input_seq = preprocess_input(txt)
    states = encoder_model.predict(input_seq)

    sos = outputs_word2index['<sos>']
    eos = outputs_word2index['<eos>']

    output_seq = np.zeros((1, 1))
    output_seq[0, 0] = sos

    output_sentence = []

    for _ in range(outputs_maxlen):
        output_tokens, h, c = decoder_model.predict([output_seq] + states)
        idx = np.argmax(output_tokens[0, 0, :])

        if idx == eos:
            break
        if idx > 0:
            word = index_to_word_output[idx]
            output_sentence.append(word)

        states = [h, c]
        output_seq[0, 0] = idx

    return ' '.join(output_sentence)

# API endpoint for translation
@app.post("/translate")
async def api_translate(request_body: TranslationRequest):
    try:
        # Extract text from request body
        input_text = request_body.text

        # Translate the input text
        translated_text = translate(input_text)

        # Return the translated text
        return {"translation": translated_text}
    except Exception as e:
        return {'error': str(e)}
