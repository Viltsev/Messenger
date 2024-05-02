import json
import os
from dotenv import load_dotenv

from openai import OpenAI
load_dotenv()

client = OpenAI(
    api_key=os.getenv("OPENAI_API_KEY"),
    base_url="https://api.proxyapi.ru/openai/v1"
)

def ask_gpt(messages: list):
    response = client.chat.completions.create(
        model="gpt-3.5-turbo",
        messages=messages
    )
    return response.choices[0].message.content

def get_question(level: str):
    question = f"""
        Generate question in different topic then I will ask for it ({level} level)  
        Output format: just string with a question
    """
    messages = [{"role": "user", "content": question}]
    return ask_gpt(messages=messages)

def send_answer(myQuestion: str, answer: str):
    question = f"""
        There is question: {myQuestion}
        It's my answer: {answer}
        Please, correct my answer (check grammar etc) and give me full explanations.
        Output format in json:
        "corrected_answer": your corrected answer
        "explanation": [
            your explanation by the points
        ]
    """
    messages = [{"role": "user", "content": question}]
    answers = ask_gpt(messages=messages)
    json_string = ''.join(answers)
    exercise_data = json.loads(json_string)
    return exercise_data