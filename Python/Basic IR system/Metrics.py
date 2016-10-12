from utils import aux_objects
from Query_launcher import Query_launcher
import numpy
import collections
import matplotlib.pyplot as plt

def get_recall(n_relevant_retrieved,n_relevant_corpus):
    return n_relevant_retrieved/n_relevant_corpus

def get_precision(n_relevant_retrieved,n_total_retrieved):
    return n_relevant_retrieved/n_total_retrieved

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

def get_metric_tuples(model_name):
    query_launcher = Query_launcher(aux_objects.corpus,model_name)
    metric_queries = list()
    levels_queries = list()
    for key,query in aux_objects.queries.items():
        ranking = query_launcher.launch_query(query)
        relevant_docs = aux_objects.relations[key]
        n_relevant_retrieved = 0
        n_total_retrieved = 0
        n_relevant_corpus = len(relevant_docs)
        metric_list = list()
        for doc,__ in ranking:
            if doc in relevant_docs:
                n_relevant_retrieved += 1
            n_total_retrieved += 1
            recall = get_recall(n_relevant_retrieved,n_relevant_corpus)
            precision = get_precision(n_relevant_retrieved,n_total_retrieved)
            metric_list.append((recall,precision))
        metric_queries.append(metric_list)
        levels_queries.append(get_recall_levels(metric_list))
    return levels_queries

def get_recall_levels(precision_recall_tuples):
    recall_levels = [0.0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1]
    levels = collections.OrderedDict()
    for level in recall_levels:
        precision_list=[i[1] for i in precision_recall_tuples if i[0]>=level]
        precision = max(precision_list)
        levels[level] = precision
    return levels

def get_average_recall_levels(model_name):
    metric_tuples = get_metric_tuples(model_name)
    average_recall_levels = collections.OrderedDict()
    for k in metric_tuples[0].keys():
        average_recall_levels[k] = numpy.mean(list(d[k] for d in metric_tuples))
    return average_recall_levels

#args: dict with the name of thee mode as key
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
    dict_model_recall_levels = dict()
    for model_name in model_names:
        dict_model_recall_levels[model_name] = get_average_recall_levels(model_name)
    plot_precision_recall(dict_model_recall_levels)
    # print(dict_model_recall_levels)