from PIL import Image
import numpy as numpy
from  skimage import io,color,novice
import pylab as P
import glob
import json
import codecs
import os

#img_name = "tests/SREF.PNG"
#img_name1 = "tests/beagleA.png"

def get_low_level_features(filename):
    image = Image.open(filename)
    im = image.convert('L')
    im_array = numpy.array(im)

    #Brightness
    h,_,_ = P.hist(im_array.flatten(),bins=3,hold = False)
    brightness_value = max(enumerate(h),key=lambda x: x[1])
    brightness_dict = {0:'dark',1:'balanced',2:'bright'}
    brightness = brightness_dict[brightness_value[0]]

    #size
    pic = novice.open(filename)
    area = pic.size[0]*pic.size[1]
    if area<=2500:
        size = 'small'
    elif area > 2500 and area <= 900000:
        size = 'medium'
    else:
        size = 'big'

    #Color
    red = []
    green = []
    blue = []
    for pixel in pic:
        red.append(pixel.red)
        green.append(pixel.green)
        blue.append(pixel.blue)
    total_RGB = [sum(red),sum(green),sum(blue)]
    color_percentage = [100*i/sum(total_RGB) for i in total_RGB]
    color_percentage_labels='red percentage - {},green percentage - {},blue percentage - {}'.format(str(round(color_percentage[0],2)),str(round(color_percentage[1],2)),str(round(color_percentage[2],2)))
    dominant_color = max(enumerate(color_percentage),key=lambda x: x[1])
    dominant_colors_dict= {0:'red',1:'green',2:'blue'}
    dominant_color_label = dominant_colors_dict.get(dominant_color[0])
    return 'brightness - {} , size - {} , dominant_color - {} , color percentage - ( {} )'.format(brightness,size,dominant_color_label,color_percentage_labels)

for dirname in os.listdir(os.getcwd()):
    for filename in glob.glob(os.path.join(dirname, '*.PNG')):
        # print(filename)
        low_level_dict = get_low_level_features(filename)
        #Reusing file names as id.
        filename=filename.replace("\\", "")
        filename=filename.replace("/", "_")
        filename=filename.replace(" ", "_")
        filename=filename.replace(".png", "")
        #Creating json Format
        jsonString = json.dumps([{'id':filename,'text':'','dcm':'','exif':'','low_level_features':low_level_dict}], sort_keys=True)
        # print(filename)
        #Creating json file
        with codecs.open("results/png/"+filename + '.json', 'w') as outfile:
            outfile.write(jsonString)
        print("Created id =" + filename)