#!/usr/bin/python
from utils import aux_objects
import Modeller
import numpy
import collections
import matplotlib.pyplot as plt
# import Metrics

class Query_launcher:

    def __init__(self,corpus,model_name):
        self.modeller = Modeller.Modeller(corpus,model_name)
        self.model = self.modeller.get_model()

    def launch_query(self,query):
        ranking = self.modeller.get_ranking(query)
        return ranking

    def print_scored_docs(self,ranking):
        for doc, score in ranking:
            if score != 0.0:
                print("[ Score = {0} ] - Doc id: {1}".format(score,doc))

    def print_top_n_docs(self,ranking,n):
        for doc, score in ranking[0:n]:
            print("[ Score = {0} ] - Doc id: {1}".format(score,doc))

    def get_query_vectors(self,query):
        return self.modeller.get_query_vectors(query)

    def get_docs_vectors(self,list_docs,weight):
        vectors_list = list()
        for doc in list_docs:
            vectors_list += self.modeller.get_doc_vectors(doc)
        #we have here a list of lists of tuples
        result = dict()
        for i,j in vectors_list:
            if i in result.keys():
                result[i] += j
            else:
                result[i] = j
        # transposed = list(map(list, zip(*vectors_list)))
        # return [sum(i)*weight for i in transposed]
        return result

    def process_query_from_vector(self,vector):
        ranking = self.modeller.get_ranking_from_vector(vector)
        return ranking

def get_recall(n_relevant_retrieved,n_relevant_corpus):
    return n_relevant_retrieved/n_relevant_corpus

def get_precision(n_relevant_retrieved,n_total_retrieved):
    return n_relevant_retrieved/n_total_retrieved

def get_recall_levels(precision_recall_tuples):
    recall_levels = [0.0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1]
    levels = collections.OrderedDict()
    for level in recall_levels:
        if len([i[0] for i in precision_recall_tuples if i[1]>=level]) != 0:
            precision_list=[i[0] for i in precision_recall_tuples if i[1]>=level]
            precision = max(precision_list)
            previous = precision
        else:
            precision = previous

        levels[level] = precision
    return levels

def get_relevant_docs(list_docs,query_id):
    relevant_docs = aux_objects.relations[query_id]
    n_relevant_retrieved = 0
    n_total_retrieved = 0
    n_relevant_corpus = len(relevant_docs)
    metric_list = list()
    for doc in list_docs:
        if doc in relevant_docs:
            n_relevant_retrieved += 1
        n_total_retrieved += 1
        recall = get_recall(n_relevant_retrieved,n_relevant_corpus)
        precision = get_precision(n_relevant_retrieved,n_total_retrieved)
        metric_list.append((recall,precision))
    return metric_list

def plot_precision_recall(dict_average_recall_levels):
    for mode_name,average_recall_levels in dict_average_recall_levels.items():
        x = list(average_recall_levels.keys())
        y = list(average_recall_levels.values())
        plt.plot(x,y,label=mode_name)
    plt.xlabel('Recall')
    plt.ylabel('Precision')
    plt.title('Recall-precision curves')
    plt.legend()
    plt.show()

if __name__ == "__main__":
    model_names = ['tf_idf','tf','logtf_probidf','logtf_smoothidf']
    print ('Welcome to Ignacio Amaya\'s Implementation of a basic IR System. '
           'Pick one of the following TF-IDF weighting schema before start making queries:\n'
           '1. TF-IDF, 2. TF, 3. log(TF), probabilistic IDF, 4. log(TF)-smooth IDF')
    model_number = 0
    while(model_number not in [1,2,3,4]):
        model_input = input('Introduce your TF-IDF weighting scheme: ')
        model_number = int(model_input)
        if model_number not in [1,2,3,4]:
            print('ERROR: invalid TF-IDF weighting schema. Valid inputs are: 1,2,3 or 4.')
    model_name = model_names[model_number-1]
    query_mode = 0
    while(query_mode not in ['1','2']):
        query_mode = input ('Do you want to use a predefined query? 1. YES, 2.NO\n')
        if query_mode not in ['1','2']:
            print('ERROR: invalid option. Valid inputs are: 1 or 2.')
    if query_mode=='1':
        query_id = '0'
        while query_id not in [str(i) for i in range(1,31)]:
            query_id = input('Select a predefine query ID (0-30): ')
            if query_id not in [str(i) for i in range(1,31)]:
                print('ERROR: invalid query id. Valid inputs are numbers from 1 to 30.')
        query = aux_objects.queries[query_id]
    else:
        query = input('Introduce your query: ')

    query_launcher = Query_launcher(aux_objects.corpus,model_name)
    ranking = query_launcher.launch_query(query)

    relevant_doc_number = -1
    while (relevant_doc_number not in range(0,31)):
        relevant_doc_number_input = input ('How many relevant documents do you want to see?\n'
                            'Pick a number between 1 and 30 or 0 if you want to see all the relevant ones: ')
        relevant_doc_number = int(relevant_doc_number_input)
        if (relevant_doc_number not in range(0,31)):
            print('ERROR: invalid input number. Valid inputs are numbers from 0 to 30.')

    if relevant_doc_number == 0:
        relevant_doc_number = len([i for i in ranking if i[1]!=0.0])
        query_launcher.print_scored_docs(ranking)
    else:
        query_launcher.print_top_n_docs(ranking,relevant_doc_number)

    automatic = '-1'

    #Automatic only presented if used a predefnied query (because for personal queries we can't make the evaluation)
    if query_mode == '1':
        while automatic not in ['1','2']:
            automatic = input ('Do you want to use automatic query improvement using Rocchio\'s formula? 1. YES, 2.NO\n')

        if automatic == '1':
            query_vector = query_launcher.get_query_vectors(query)
            queries_list = list()
            queries_list.append(query)
            previous_precision_avg = -1
            precision_avg = 0
            dict_average_recall_levels = dict()
            counter = 1
            while precision_avg>previous_precision_avg:
                list_docs = [i[0] for i in ranking[0:relevant_doc_number]]
                all_docs = [i[0] for i in ranking]
                recall_levels = get_relevant_docs(all_docs,query_id)
                previous_precision_avg = precision_avg
                precision_avg = numpy.mean([i[1] for i in recall_levels])
                if precision_avg > previous_precision_avg:
                    dict_average_recall_levels[str(counter)] = get_recall_levels(recall_levels)
                    counter += 1
                rocchio_relevant = [i for i in aux_objects.relations[query_id] if i in list_docs]
                rocchio_non_relevant = [i for i in list_docs if i not in rocchio_relevant]
                add_vector = query_launcher.get_docs_vectors(rocchio_relevant,1)
                substract_vector = query_launcher.get_docs_vectors(rocchio_non_relevant,0.5)
                for word,weight in query_vector:
                    if word in add_vector.keys():
                        weight += add_vector.pop(word)
                    if word in substract_vector.keys():
                        weight -= substract_vector.pop(word)
                        if weight < 0:
                            weight = 0
                for key,value in add_vector.items():
                    query_vector.append((key,value))
                ranking = query_launcher.process_query_from_vector(query_vector)

            #TODO:interpolate recall levels
            plot_precision_recall(dict_average_recall_levels)
            # print(dict_average_recall_levels)