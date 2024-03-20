import g4f
import json

def ask_gpt(messages: list):
    response = g4f.ChatCompletion.create(
        model=g4f.models.gpt_35_turbo,
        messages=messages,
        provider=g4f.Provider.FreeGpt,
        stream=True
    )
    result = ""
    for i in response:
        result = result + i
    return result

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