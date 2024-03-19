from fastapi import FastAPI
from pydantic import BaseModel
import SentencesExercise

app = FastAPI()

class AnswerInput(BaseModel):
    answers: list

@app.post("/get_data")
def getData(answers_input: AnswerInput):
    answers = answers_input.answers
    result, new_answers = SentencesExercise.main(answers)
    return {"answer": result,
            "answers_array": new_answers}

@app.get("/")
async def root():
    return {"message": "Welcome to the Sentences Exercise API"}
