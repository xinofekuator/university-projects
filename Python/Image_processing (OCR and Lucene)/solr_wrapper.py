import requests
import os
import json

core_name = 'core1'
folder = 'results'

def upload_docs(files_folder,core_name):
    headers = {'content-type': 'application/json'}
    files_list = []
    for dirName, subdirList, fileList in os.walk(files_folder):
        for filename in fileList:
            if ".json" in filename.lower():  # check is the file is a json
                files_list.append(os.path.join(dirName,filename))
    for file in files_list:
        json_data=open(file).read()
        data = json.loads(json_data)
        r1 = requests.post('http://localhost:8983/solr/'+ core_name +'/update', data=json.dumps(data), headers=headers)
        if r1.ok != True:
            print('ERROR: the file {} could not be loaded'.format(file))
        else:
            print('File {} correctly uploaded to solr'.format(file))


#url_example: http://localhost:8983/solr/core1/select?q=ISO_IR+100&fl=id&wt=json&indent=true
#input text is a list of the words to look (or a single string if the length is 1)
#output_fields is a list with the fields to retrieve (or a single string if the length is 1)
def query_docs(input_text,output_fields,core_name):
    query = {'q': input_text, 'fl': output_fields, 'wt':'json', 'indent':True}
    #gets XML responses
    r = requests.get('http://localhost:8983/solr/'+ core_name +'/select', params=query)
    if r.ok:
        json_text = json.loads(r.text)
        response = json_text['response']['docs']
    else:
        response = None
    return response


upload_docs(folder,core_name)
#tests
r1 = query_docs('The',['id','text'],core_name)
r2 = query_docs(['ISO_IR 100'],['id','text','dcm'],core_name)
r3 = query_docs(['dark'],['id','low_level_features'],core_name)
