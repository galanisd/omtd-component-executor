url="http://nactem.ac.uk/api/openminted/chebi"
ts="uk.ac.nactem.uima_ChebiCurationTypeSystem_0.1"
inD=../testOutput_de.tudarmstadt.ukp.dkpro.core.io.pdf.PdfReader2/
otD=../testOutput_Chebi/

bash ../Linux_runWS.sh -input $inD -output $otD -Pwsurl=$url -Ptypesystems=$ts