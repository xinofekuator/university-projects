
#PyOcr.py
#Information Retrieval
#Created by Hugo Santana and Ignacio Amaya

from PIL import Image
import sys

import pyocr
import pyocr.builders
import codecs #Needed for UTF Conversion
import json #json converter library
import glob #Used to navigate throught system paths
import os   #Used to navigate throught system paths
import dicom #dicom library, do not use import pydicom
import exifread #exis library

def scanOcr_Exif(tool, lang):
    for dirname in os.listdir(os.getcwd()): 
        for filename in glob.glob(os.path.join(dirname, '*.tif')):
            print("file = " + filename)
            #Getting text from image using ocr
            txtOcr = tool.image_to_string(
                Image.open(filename),
                lang=lang,
                builder=pyocr.builders.TextBuilder()
            )

            #Getting text from image using exif
            f=open(filename,'rb')
            txtExif = str(exifread.process_file(f))
            print(txtExif)
            #Reusing file names as id.
            filename=filename.replace("\\", "")
            filename=filename.replace("/", "_")
            filename=filename.replace(".tif", "")
        
            #Creating json Format
            jsonString = json.dumps([{'id':filename,'text':txtOcr,'dcm':'','exif':txtExif}], sort_keys=True, indent=4)

            #Creating json file
            with codecs.open("results/ocr_exif/"+filename + '.json', 'w') as outfile:
                outfile.write(jsonString)
    return        

def scanDicom(tool):
    for dirname in os.listdir(os.getcwd()): 
        for filename in glob.glob(os.path.join(dirname, '*.dcm')):
            print("file = "+filename)
            #Getting text from image using dicom
            txt = str(dicom.read_file(filename))
            #Reusing file names as id.
            filename=filename.replace("\\", "")
            filename=filename.replace("/", "_")
            filename=filename.replace(" ", "_")
            filename=filename.replace(".dcm", "")
            print(txt)
            #Creating json Format
            jsonString = json.dumps([{'id':filename,'text':'','dcm':txt,'exif':''}], sort_keys=True, indent=4)

            #Creating json file
            with codecs.open("results/dicom/"+filename + '.json', 'w') as outfile:
                outfile.write(jsonString)
            print("Created id =" + filename)
    return 

def start():
    tools = pyocr.get_available_tools()
    if len(tools) == 0:
        print("No OCR tool found")
        sys.exit(1)
    # The tools are returned in the recommended order of usage
    tool = tools[0]
    print("Will use tool '%s'" % (tool.get_name()))

    #Selecting language
    langs = tool.get_available_languages()
    #Using English
    lang = langs[0] 


    #Going around all the files on the /files directory
    #Program should be ran on the root directory, all files should be on /files folder
    scanOcr_Exif(tool,lang)
    scanDicom(tool)
