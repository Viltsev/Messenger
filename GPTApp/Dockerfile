FROM python:latest
WORKDIR /gpt_app

ENV PYTHONDONTWRITEBYTECODE 1
ENV PYTHONUNBUFFERED 1

ADD requirements.txt requirements.txt
RUN pip install --no-cache-dir --upgrade -r requirements.txt

COPY ./ .

ENTRYPOINT ["uvicorn", "ExercisesController:app", "--host", "0.0.0.0", "--port", "8000"]
