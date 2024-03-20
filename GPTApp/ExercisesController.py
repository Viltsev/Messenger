from fastapi import FastAPI
import SentencesExercise
import QuestionsExercise

app = FastAPI()


@app.get("/get_sentence_exercise")
def getData(topic: str):
    return SentencesExercise.main(topic)

@app.get("/get_question")
def getQuestion(level: str):
    return QuestionsExercise.get_question(level)

@app.get("/send_answer")
def sendAnswer(question: str, answer: str):
    return QuestionsExercise.send_answer(question, answer)
