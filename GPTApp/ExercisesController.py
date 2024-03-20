from fastapi import FastAPI
import SentencesExercise
import QuestionsExercise
import TranslationExercise

app = FastAPI()


@app.get("/get_sentence_exercise")
def getSentenceExercise(topic: str):
    return SentencesExercise.main(topic)

@app.get("/get_question")
def getQuestion(level: str):
    return QuestionsExercise.get_question(level)

@app.get("/send_answer")
def sendAnswer(question: str, answer: str):
    return QuestionsExercise.send_answer(question, answer)

@app.get("/get_translation_exercise")
def getTranslationExercise(topic: str, level: str):
    return TranslationExercise.getText(topic, level)

@app.get("/send_translation_exercise")
def sendTranslationExercise(original_text: str, text: str, level: str):
    return TranslationExercise.sendTextToCheck(original_text, text, level)
