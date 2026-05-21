# BeatStreams

An online music streaming platform with mood-based song recommendation powered by Machine Learning and MongoDB.

## Overview

BeatStreams is a full-stack music streaming web application where users can:

* Create accounts and log in securely
* Stream songs directly from the website
* Browse albums and single tracks
* Search songs instantly
* Detect the mood/emotion of uploaded songs using AI
* Get recommended songs based on detected mood

The project combines Java web technologies with a Python-based AI recommendation engine for emotion detection and music similarity analysis.

---

## Features

### User Authentication

* User registration and login system
* Username & password authentication
* Session-based access

### Music Streaming

* Play songs online
* Browse albums and singles
* Dynamic music player interface

### Search System

* Search songs by:

  * Song name
  * Album name

### Mood Detection AI

Upload a song and the system predicts moods such as:

* Happy
* Sad
* Calm
* Romantic
* Energetic

### Recommendation System

Based on the detected mood, BeatStreams suggests similar songs using:

* CNN embeddings
* Cosine similarity
* MongoDB song metadata

---

## Tech Stack

### Frontend

* HTML
* CSS
* JavaScript

### Backend

* Java Servlets
* Flask (Python AI service)

### Database

* MongoDB

### Machine Learning

* TensorFlow / Keras
* Librosa
* OpenCV
* CNN-based emotion classifier

---

## Project Structure

```bash
BeatStreams/
│
├── Web Pages/
│   ├── images/
│   ├── songs/
│   ├── index.html
│   ├── login.html
│   ├── player.html
│   ├── register.html
│   └── register-success.html
│
├── Source Packages/
│   ├── com.beatstreams/
│   ├── com.beatstreams.database/
│   │   ├── MongoConnection.java
│   │   └── SongLoader.java
│   │
│   ├── com.beatstreams.servlet/
│   │   ├── LoginServlet.java
│   │   ├── RegisterServlet.java
│   │   ├── SongServlet.java
│   │   └── UserServlet.java
│
├── app.py
├── best_deam_cnn_model.keras
├── song_embeddingsfinal.pkl
└── song_metadatafinal.csv
```

---

## Machine Learning Workflow 

The AI recommendation engine:

1. Loads audio files
2. Converts them into Mel Spectrograms
3. Extracts CNN embeddings
4. Predicts song emotion
5. Finds similar songs using cosine similarity
6. Returns recommended tracks

The Flask API handles:

* `/predict` → mood detection & recommendations
* `/search-songs` → song search API

Implementation details are available in the Flask backend source. 

---

## Installation 

### Clone Repository

```bash
git clone https://github.com/yourusername/BeatStreams.git
cd BeatStreams
```

### Setup Java Backend

* Import project into NetBeans or Eclipse
* Configure Apache Tomcat server
* Add MongoDB dependencies

### Setup Python AI Backend

Install dependencies:

```bash
pip install flask flask-cors tensorflow librosa numpy opencv-python matplotlib pandas pymongo
```

Run Flask server:

```bash
python app.py
```

### Start MongoDB

```bash
mongod
```

---

## API Endpoints 

### Predict Song Mood

```http
POST /predict
```

Upload:

* `.mp3`
* `.wav`

Returns:

* Detected emotion
* Spectrogram image
* Recommended songs

### Search Songs

```http
GET /search-songs?q=songname
```

---

## Screenshots 📸


---

## Future Improvements 

* Playlist creation
* Like/Favorite songs
* Real-time music streaming
* Spotify API integration
* JWT Authentication
* User listening history
* Better recommendation algorithms
* Mobile responsive UI

---

## Learning Outcomes 

This project helped in understanding:

* Java Servlet development
* REST APIs
* MongoDB integration
* Audio signal processing
* Deep Learning for music emotion recognition
* Recommendation systems
* Full-stack application architecture

---

## Author 

Sarthak Chatterjee
Parthiv Sarkar

---

## License 

This project is for educational purposes.
