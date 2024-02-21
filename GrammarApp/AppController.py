from fastapi import FastAPI
from CorrectionService import correctText
from pydantic import BaseModel

app = FastAPI()

class Correction(BaseModel):
    edit: str
    replaceWord: str
    startPosition: int
    endPosition: int

@app.get("/checkGrammar/{text}")
async def check_grammar(text):
    correctionsArray = correctText(text)
    correction_objects = [Correction(**c) for c in correctionsArray]
    return correction_objects