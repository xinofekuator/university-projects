import csv
import sys

class owl_files:

    def __init__(self, ger_file, sgp_file):
        self.ger_file = ger_file
        self.sgp_file = sgp_file
        self.subjects_ger = self.get_subjects_ger()[0]
        self.subjects_mapping = self.get_subjects_ger()[1]
        self.subjects_sgp = self.get_subjects_sgp()

    def write_owl_instances(self,out_file):
        with open(out_file, 'w+') as out:
            with open(self.ger_file,'r') as f_ger:
                reader_ger = csv.DictReader(f_ger, delimiter=';')
                #2 Count_germany per row,1 only Semester and 1 Subject per row
                out.write(self.generate_individual1("WT_2014-15",'Semester',"is_part_of","2015"))
                semester = "WT_2014-15"
                for row in reader_ger:
                    subject = row['Subject'].replace(' ', '_')
                    count_dict = {'male':row['Total Male'],'female':row['Total Female']}
                    out.write(self.generate_individual2(''.join((subject,'_',semester)),'Subject',"is_in_semester",semester,
                                              "is_of_field",self.subjects_mapping[subject]) + '\n')
                    for gender,count in count_dict.items():
                        out.write(self.generate_counter_individual(''.join((subject,'_',semester,'_',gender)),"Count_germany",
                                                         "is_counting_subject",subject,"is_in_semester",semester,
                                                         "Germany",gender,"semester",count,"matriculated")+'\n')

            with open(self.sgp_file,'r') as f_sgp:
                reader_sgp = csv.DictReader(f_sgp, delimiter=';')
                #Count_sinagapore per row,some Year and Subject_type from list
                years = [str(i) for i in range(1993,2014)]
                for year in years:
                    out.write(self.generate_simple_individual(year,"Year"))
                for subject_type in self.subjects_sgp:
                    out.write(self.generate_simple_individual(subject_type,"Subject_type"))
                for row in reader_sgp:
                    year = row['year']
                    subject_type = self.get_subject_type_name(row['type_of_course'])
                    count = row['no_of_graduates']
                    gender = row['sex']
                    out.write(self.generate_counter_individual(''.join((subject_type,'_',year,'_',gender)),"Count_singapore",
                                                         "is_counting_subject_type",subject_type,"is_in_year",year,
                                                         "Germany",gender,"year",count,"graduated")+'\n')


    def get_subjects_ger(self):
        subjects_ger = list()
        subjects_mapping = dict()
        with open(self.ger_file,'r') as f:
            reader = csv.DictReader(f, delimiter=';')
            for row in reader:
                subject_type_name = self.get_subject_type_name(row['SubjectType'])
                subject_name = row['Subject'].replace(' ', '_')
                subjects_ger.append(subject_name)
                subjects_mapping[subject_name] = subject_type_name
            return [subjects_ger,subjects_mapping]

    def get_subjects_sgp(self):
        subjects_sgp = list()
        with open(self.sgp_file,'r') as f:
            reader = csv.DictReader(f, delimiter=';')
            for row in reader:
                subject_type_name = self.get_subject_type_name(row['type_of_course'])
                subjects_sgp.append(subject_type_name)
        return subjects_sgp

    def get_subject_type_name(self,unparsed_name):
        parsed_name = unparsed_name.replace(' ','_')
        parsed_name = parsed_name.replace(',','')
        parsed_name = parsed_name.replace('&','and')
        parsed_name = 'ST_' + parsed_name.lower()
        return parsed_name

    """
    <!--http://www.semanticweb.org/educationMapping#year_2015 -->

    <owl:NamedIndividual rdf:about="http://www.semanticweb.org/educationMapping#year_2015">
        <rdf:type rdf:resource="http://www.semanticweb.org/educationMapping#Year"/>
    </owl:NamedIndividual>
    """
    #Year,Subject_type
    def generate_simple_individual(self,name,domain):
        return     '<!--http://www.semanticweb.org/educationMapping#{0} -->\n\n' \
                   '<owl:NamedIndividual rdf:about="http://www.semanticweb.org/educationMapping#{0}">\n' \
                   '<rdf:type rdf:resource="http://www.semanticweb.org/educationMapping#{1}"/>\n' \
                   '</owl:NamedIndividual>\n\n\n\n'\
            .format(name,domain)

    """
    <!--http://www.semanticweb.org/educationMapping#arts_applied -->

    <owl:NamedIndividual rdf:about="http://www.semanticweb.org/educationMapping#arts_applied">
    <rdf:type rdf:resource="http://www.semanticweb.org/educationMapping#Subject"/>
    <is_of_field rdf:resource="http://www.semanticweb.org/educationMapping#arts_general"/>
    </owl:NamedIndividual>
    """
    #Subject,Semester
    def generate_individual1(self,name,domain,object_property1,range1):
        return     '<!--http://www.semanticweb.org/educationMapping#{0} -->\n\n' \
                   '<owl:NamedIndividual rdf:about="http://www.semanticweb.org/educationMapping#{0}">\n' \
                   '<rdf:type rdf:resource="http://www.semanticweb.org/educationMapping#{1}"/>\n' \
                   '<{2} rdf:resource="http://www.semanticweb.org/educationMapping#{3}"/>\n' \
                   '</owl:NamedIndividual>\n\n\n\n'\
            .format(name,domain,object_property1,range1)

    def generate_individual2(self,name,domain,object_property1,range1,object_property2,range2):
        return     '<!--http://www.semanticweb.org/educationMapping#{0} -->\n\n' \
                   '<owl:NamedIndividual rdf:about="http://www.semanticweb.org/educationMapping#{0}">\n' \
                   '<rdf:type rdf:resource="http://www.semanticweb.org/educationMapping#{1}"/>\n' \
                   '<{2} rdf:resource="http://www.semanticweb.org/educationMapping#{3}"/>\n' \
                   '<{4} rdf:resource="http://www.semanticweb.org/educationMapping#{5}"/>\n' \
                   '</owl:NamedIndividual>\n\n\n\n'\
            .format(name,domain,object_property1,range1,object_property2,range2)

    """
    <!--http://www.semanticweb.org/educationMapping#count_germany_arts_2015 -->

    <owl:NamedIndividual rdf:about="http://www.semanticweb.org/educationMapping#count_germany_arts_2015">
    <rdf:type rdf:resource="http://www.semanticweb.org/educationMapping#Count_germany"/>
    <is_counting_subject_type rdf:resource="http://www.semanticweb.org/educationMapping#arts_applied"/>
    <is_in_semester rdf:resource="http://www.semanticweb.org/educationMapping#semester_2015"/>
    <count_country rdf:datatype="http://www.w3.org/2001/XMLSchema#string">germany</count_country>
    <count_gender rdf:datatype="http://www.w3.org/2001/XMLSchema#string">male</count_gender>
    <count_time_period rdf:datatype="http://www.w3.org/2001/XMLSchema#string">semester</count_time_period>
    <count_value rdf:datatype="http://www.w3.org/2001/XMLSchema#int">8</count_value>
    </owl:NamedIndividual>
    """

    #Count_germany,Count_singapore
    def generate_counter_individual(self,name,domain,object_property1,range1,object_property2,range2,
                                    country,gender,time_period,value,subjective):
        return     '<!--http://www.semanticweb.org/educationMapping#{0} -->\n\n' \
                   '<owl:NamedIndividual rdf:about="http://www.semanticweb.org/educationMapping#{0}">\n' \
                   '<rdf:type rdf:resource="http://www.semanticweb.org/educationMapping#{1}"/>\n' \
                   '<{2} rdf:resource="http://www.semanticweb.org/educationMapping#{3}"/>\n' \
                   '<{4} rdf:resource="http://www.semanticweb.org/educationMapping#{5}"/>\n' \
                   '<count_country rdf:datatype="http://www.w3.org/2001/XMLSchema#string">{6}</count_country>\n' \
                   '<count_gender rdf:datatype="http://www.w3.org/2001/XMLSchema#string">{7}</count_gender>\n'\
                   '<count_time_period rdf:datatype="http://www.w3.org/2001/XMLSchema#string">{8}</count_time_period>\n'\
                   '<count_value rdf:datatype="http://www.w3.org/2001/XMLSchema#int">{9}</count_value>\n'\
                   '<count_subjective rdf:datatype="http://www.w3.org/2001/XMLSchema#string">{10}</count_subjective>\n'\
                   '</owl:NamedIndividual>\n\n\n\n'\
            .format(name,domain,object_property1,range1,object_property2,range2,country,gender,time_period,value,subjective)


if __name__ == '__main__':

    if len(sys.argv)==4:
        owl_files(sys.argv[1],sys.argv[2]).write_owl_instances(sys.argv[3])
    else:
        print('USAGE: 3 args needed --> ger_file sgp_file out_file')