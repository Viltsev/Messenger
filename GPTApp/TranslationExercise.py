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

def getText(topic: str, level: str):
    question = f"""
        Generate text in Russian. Topic: {topic}.
        Then I will translate it to English, that is why text should be {level} level complexity.
        Output format: just text
    """
    messages = [{"role": "user", "content": question}]
    return ask_gpt(messages=messages)


def sendTextToCheck(original: str, text: str, level: str):
    question = f"""
            I have text in Russian: {original}.
            I've translated it to English: {text}.
            Check my translation and correct it, check grammar etc.
            It should be {level} complexity.
            Output format in json:
                "corrected_text": your corrected answer
                "explanations": [
                    your explanation by the points
                ]
        """
    messages = [{"role": "user", "content": question}]
    answers = ask_gpt(messages=messages)
    json_string = ''.join(answers)
    exercise_data = json.loads(json_string)
    return exercise_data


# answer = getText("Sport", "B2")
# print(answer)
# original_text = """
#     Сегодня я проснулся и решил сделать что-то необычное. Решил пойти в парк и покормить уток. Погода была прекрасная, солнце светило ярко, птицы щебетали вокруг. Я купил хлеб и начал кидать его уткам. Они были такие голодные и радостные, что меня это просто поразило. После этого я пошел гулять по парку, наслаждаясь спокойствием и красотой природы.
# """
#
# translation = """
#     I've woke up and solved to do unusual. I've decided to visit park and feed ducks. The weather was beautiful, the sun was shining brightly, birds were chirping around. I bought some bread and started throwing it to the ducks. They were so hungry and joyful that it simply amazed me. After that, I went for a walk in the park, enjoying the tranquility and beauty of nature.
# """
#
# answer = sendTextToCheck(original_text, translation, "B2")
# print(answer)