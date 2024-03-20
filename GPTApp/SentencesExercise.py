import g4f
import json

def ask_gpt(messages: list):
    response = g4f.ChatCompletion.create(
        model=g4f.models.gpt_35_turbo,
        messages=messages,
        provider=g4f.Provider.FreeGpt,
        stream=True
    )
    result = []
    for i in response:
        result.append(i)
    return result

def main(topic):
    question = f"""
        Generate 15 exercises topic: {topic}
        It should be sentences with missed words.
        Give me it ONLY in json format, like:
            "exercise": string,
            "right_answer": string    
    """

    messages = [{"role": "user", "content": question}]
    answers = ask_gpt(messages=messages)

    json_string = ''.join(answers)
    exercise_data = json.loads(json_string)
    return exercise_data