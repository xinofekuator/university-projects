sample_corpus = [
    "Human machine interface for lab abc computer applications",
    "A survey of user opinion of computer system response time",
    "The EPS user interface management system",
    "System and human system engineering testing of EPS",
    "Relation of user perceived response time to error measurement",
    "The generation of random binary unordered trees",
    "The intersection graph of paths in trees",
    "Graph minors IV Widths of trees and well quasi ordering",
    "Graph minors A survey"
]


def create_dict_from_file(filename):
    with open(filename,mode='r') as f:
        corpus = ''.join(f.readlines())
    texts = corpus.split('.I ')
    del texts[0]
    dict_corpus = dict()
    for text in texts:
        aux = text.split('.W\n')
        dict_corpus[aux[0].replace('\n','')] = aux[1]
    return dict_corpus

def get_relations(filename):
    relations_dict = dict()
    with open(filename,mode='r') as f:
        for line in f.readlines():
            aux = line.split(' ')
            if aux[0] not in relations_dict.keys():
                relations_dict[aux[0]] = list()
            relations_dict[aux[0]].append(aux[2])
    return relations_dict


corpus = create_dict_from_file('MED.ALL')
queries = create_dict_from_file('MED.QRY')
relations = get_relations('MED.REL')
