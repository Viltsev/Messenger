from fastapi import FastAPI
import SentencesExercise

app = FastAPI()

@app.get("/get_data")
def getData(topic: str):
    result = SentencesExercise.main(topic)
    return result