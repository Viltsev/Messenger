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
    return resultText