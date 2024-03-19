import g4f
import json

def ask_gpt(messages: list) -> str:
    response = g4f.ChatCompletion.create(
        model=g4f.models.gpt_4,
        messages=messages,
        provider=g4f.Provider.You,
        stream=True
    )
    return response


def main(answers: list):
    question = """
        Generate 15 exercises topic: All conditionals – mixed conditionals, alternatives to if, inversion
        It should be sentences with missed words.
        Give me it ONLY in json format, like:
        {
            "exercise": string,
            "right_answer": string
        }
    """

    messages = [
        {"role": "assistant", "content": answer} for answer in answers
    ]
    messages.append({"role": "user", "content": question})

    answer = ask_gpt(messages=messages)

    answer_list = list(answer)
    json_data = ''.join(answer_list).split("```json")[1].split("```")[0].strip()

    exercise_data = json.loads(json_data)
    answers.append(json.dumps(exercise_data, indent=4))
    return exercise_data, answers
