<h1 align="center">
  <img align="center" src="images\logoBahasain.png"  width="250" height="250"></img>
<br><br>
Machine Learning Path
</h1>

<!-- ABOUT THE PROJECT -->
## About The Project
<p align="justify">
The machine learning workflow involves a series of steps to develop and deploy the models for word classification and translation. It begins with preparing datasets for POS tagging (from IndoNLU) and translation (from corpus), followed by data cleaning and augmentation to enhance dataset quality and diversity. The dataset is then split into training, development, and testing sets. The POS Tagging Model classifies words by their grammatical roles, while the Translation Model translates English sentences to Bahasa Indonesia using Encoder-Decoder architecture with embedding layers and LSTM units for processing input and output sequences. The models are trained and validated on respective datasets, tested for generalization, and saved in .h5 format. Finally, Flask is used to deploy the models as APIs, enabling real-time word classification and translation within the application.
</p>

<div align="center">
  <img align="center" src="images\feature.png"  width="400" height="400"></img>
</div>

## MODELS
### 1. Word Classification
<div align="center">
  <img align="center" src="images\classificationArchitechture.png"  width="500" height="400"></img>
  <img align="center" src="images\classificationAccuracy.png"  width="500" height="400"></img>
</div>
<p align="justify">
The Classification model uses a deep learning model using TensorFlow and Keras for sequence labeling tasks. It uses Bidirectional LSTMs and includes Dropout and Layer Normalization to improve training stability and reduce overfitting. The TimeDistributed Dense layer extracts features, and the final softmax layer predicts output classes. The model is optimized with Adam and trained using categorical cross-entropy loss.
</p>

### 2. Translation (EN-ID)
<div align="center">
  <img align="center" src="images\translateArchitechture.png"  width="500" height="400"></img>
  <img align="center" src="images\translateaAccuracy.png"  width="500" height="400"></img>
</div>
<p align="justify">
The Translate model uses a sequence-to-sequence model using TensorFlow and Keras. It includes an Encoder-Decoder architecture with embedding layers and LSTM units for processing input and output sequences. The model is compiled with the RMSprop optimizer and categorical cross-entropy loss for training.
</p>

### Dataset
**Our Dataset Link:**

* Word Classification
  * [POS TAGGING Datasets](https://github.com/IndoNLP/indonlu/tree/master/dataset/bapos_pos-idn)
  
* Translation (EN-ID)
  * [Neulab Corpus Datasets](https://opus.nlpl.eu/NeuLab-TedTalks/en&id/v1/NeuLab-TedTalks)
  * [Tatoeba Corpus Datasets](https://opus.nlpl.eu/Tatoeba/en&id/v2023-04-12/Tatoeba)

###  Library
* ![Pandas](https://img.shields.io/badge/pandas-%23150458.svg?style=for-the-badge&logo=pandas&logoColor=white) 
* ![NumPy](https://img.shields.io/badge/numpy-%23013243.svg?style=for-the-badge&logo=numpy&logoColor=white) 
* ![Matplotlib](https://img.shields.io/badge/Matplotlib-%23ffffff.svg?style=for-the-badge&logo=Matplotlib&logoColor=black) 
* ![Python](https://img.shields.io/badge/python-3670A0?style=for-the-badge&logo=python&logoColor=ffdd54) 
* ![TensorFlow](https://img.shields.io/badge/TensorFlow-%23FF6F00.svg?style=for-the-badge&logo=TensorFlow&logoColor=white) 
* ![Keras](https://img.shields.io/badge/Keras-%23D00000.svg?style=for-the-badge&logo=Keras&logoColor=white) 
* ![scikit-learn](https://img.shields.io/badge/scikit--learn-%23F7931E.svg?style=for-the-badge&logo=scikit-learn&logoColor=white)

<!-- CONTACT -->
## Contact

BahasaIn - [@BahasainApp](https://youtube.com/@bahasainapp?si=oIAY0DYt4onk3ETp) - YOUTUBE CHANNEL

Project Link: [https://github.com/Bapakee/Bahasain/tree/main/Machine-Learning](https://github.com/Bapakee/Bahasain/tree/main/Machine-Learning)

<p align="right">(<a href="#readme-top">back to top</a>)</p>
