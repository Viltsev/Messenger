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
    print(response)
    return response.choices[0].message.content

def main(topic):
    question = f"""
        Generate 15 exercises topic: {topic}
        It should be sentences with missed words.
        Give me it ONLY in json format, like:
        [
             "exercise": string,
             "right_answer": string
        ]  
    """

    messages = [{"role": "user", "content": question}]
    answers = ask_gpt(messages=messages)
    json_string = ''.join(answers)
    # json_string = json_string.replace('```', '')
    # json_string = json_string.replace('json', '')
    exercise_data = json.loads(json_string)
    return exercise_data

main("Present continuous")