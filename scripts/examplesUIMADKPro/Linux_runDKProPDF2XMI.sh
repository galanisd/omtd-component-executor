coordinates=de.tudarmstadt.ukp.dkpro.core_de.tudarmstadt.ukp.dkpro.core.io.pdf-asl_1.9.0-SNAPSHOT
className=de.tudarmstadt.ukp.dkpro.core.io.pdf.PdfReader
inD=../testInput/
otD=../testOutput_$className/

bash ../Linux_runUIMA.sh $coordinates $className $inD $otD -Ppatterns=[+]**/*.pdf -Planguage=en
