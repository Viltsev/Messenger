from fastapi import FastAPI
from CorrectionService import correctText

app = FastAPI()

@app.get("/checkGrammar/{text}")
async def read_item(text):
    result = correctText(text)
    return {"currentText": result}