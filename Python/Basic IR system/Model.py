import math
from gensim import models

def tf_idf_local(term_frequency):
    return term_frequency

def tf_idf_global(doc_freq,total_docs):
    return math.log(total_docs / doc_freq)

def tf_local(term_frequency):
    return term_frequency

def tf_global(doc_freq,total_docs):
    return 1

def logtf_probidf_local(term_frequency):
    return 1 + math.log(term_frequency)

def logtf_probidf_global(doc_freq,total_docs):
    return math.log((total_docs - doc_freq) / doc_freq)

def logtf_smoothidf_local(term_frequency):
    return 1 + math.log(term_frequency)

def logtf_smoothidf_global(doc_freq,total_docs):
    return math.log(1 + (total_docs/doc_freq))

class Model:

    #Global: idf
    #Local: tf
    def __init__(self,model_name):
        self.model_mapping = {'tf_idf':[tf_idf_local,tf_idf_global],
                              'tf':[tf_local,tf_global],
                              'logtf_probidf':[logtf_probidf_local,logtf_probidf_global],
                              'logtf_smoothidf':[logtf_smoothidf_local,logtf_smoothidf_global]}
        self.model_name = model_name

    def get_wlocal(self):
        return self.model_mapping[self.model_name][0]

    def get_wglobal(self):
        return self.model_mapping[self.model_name][1]