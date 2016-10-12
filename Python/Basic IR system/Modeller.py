#!/usr/bin/python
from nltk.tokenize import wordpunct_tokenize
from nltk.corpus import stopwords
from nltk.stem import PorterStemmer
from gensim import corpora, models, similarities
from operator import itemgetter
from Model import Model
from utils import aux_objects

class Modeller:

    def __init__(self,corpus,model_name):
        self.processed_corpus = None
        self.vectors = None
        self.preprocess_corpus(corpus)
        self.model = self.compute_model(model_name)
        self.similarity_matrix = self.get_similarity_matrix()

    def preprocess_document(self,doc):
        stopset = set(stopwords.words('english'))
        stemmer = PorterStemmer()
        tokens = wordpunct_tokenize(doc)
        clean = [token.lower() for token in tokens if token.lower() not in stopset and len(token) > 2]
        preprocessed_doc = [stemmer.stem(word) for word in clean]
        return preprocessed_doc

    def preprocess_corpus(self,docs):
        processed_docs = [self.preprocess_document(doc) for doc in docs.values()]
        self.processed_corpus = corpora.Dictionary(processed_docs)
        self.vectors = [self.processed_corpus.doc2bow(doc) for doc in processed_docs]

    def compute_model(self,name_code):
        model_used = Model(name_code)
        tf = model_used.get_wlocal()
        idf = model_used.get_wglobal()
        # print(self.vectors)
        return models.TfidfModel(self.vectors,wglobal=idf,wlocal=tf)

    def get_model(self):
        return self.model

    def get_similarity_matrix(self):
        return similarities.MatrixSimilarity(self.vectors, num_features=len(self.processed_corpus))

    def get_processed_corpus(self):
        return self.processed_corpus

    def process_query(self,query):
        processed_query = self.preprocess_document(query)
        vector_query = self.processed_corpus.doc2bow(processed_query)
        qtfidf = self.model[vector_query]
        similarity = self.similarity_matrix[qtfidf]
        keys = [key for key in aux_objects.corpus.keys()]
        ranking = sorted(zip(keys,similarity), key=itemgetter(1), reverse=True)
        return ranking

    def get_ranking_from_vector(self,vector_query):
        similarity = self.similarity_matrix[vector_query]
        keys = [key for key in aux_objects.corpus.keys()]
        ranking = sorted(zip(keys,similarity), key=itemgetter(1), reverse=True)
        return ranking

    def get_query_vectors(self,query):
        processed_query = self.preprocess_document(query)
        vector_query = self.processed_corpus.doc2bow(processed_query)
        qtfidf = self.model[vector_query]
        return qtfidf

    def get_doc_vectors(self,doc):
        processed_query = self.preprocess_document(aux_objects.corpus[doc])
        vector_query = self.processed_corpus.doc2bow(processed_query)
        qtfidf = self.model[vector_query]
        return qtfidf

    def get_ranking(self,query):
        return self.process_query(query)