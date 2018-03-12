url="http://nactem.ac.uk/api/openminted/neuroscience"
ts="uk.ac.nactem.uima_NeuroscienceTypeSystem_0.2"
inD=../testOutput_de.tudarmstadt.ukp.dkpro.core.io.pdf.PdfReader2/
otD=../testOutput_Neuro/

bash ../Linux_runWS.sh -input $inD -output $otD -Pwsurl=$url -Ptypesystems=$ts
