import re
from gramformer import Gramformer
import torch
import spacy
spacy.load("en_core_web_sm")

def set_seed(seed):
  torch.manual_seed(seed)
  if torch.cuda.is_available():
    torch.cuda.manual_seed_all(seed)

set_seed(1212)

gf = Gramformer(models = 1, use_gpu=False) # 1=corrector, 2=detector

def correctText(str):
    correctedText = gf.correct(str, max_candidates=1).pop()
    resultText = gf.highlight(str, correctedText)
    correctionsArray = make_corrections_array(str, resultText)
    return correctionsArray


def extract_corrections(corrected_string):
    corrections = []
    pattern = re.compile(r"<c type='([^']+)' edit='([^']+)'>([^<]+)</c>")
    matches = pattern.findall(corrected_string)
    for match in matches:
        edit_type, edit, replace_word = match
        correction = {
            "edit": edit,
            "replaceWord": replace_word
        }
        corrections.append(correction)
    return corrections

def find_positions(initial_string, corrections):
    positions = []
    start_position = 0
    for correction in corrections:
        edit_word = correction["edit"]
        replace_word = correction["replaceWord"]
        start_position = initial_string.find(replace_word, start_position)
        end_position = start_position + len(replace_word)
        positions.append({
            "edit": edit_word,
            "replaceWord": replace_word,
            "startPosition": start_position,
            "endPosition": end_position
        })
        start_position = end_position
    return positions

def make_corrections_array(initialString, correctedString):
    corrections = extract_corrections(correctedString)
    positions = find_positions(initialString, corrections)
    return positions