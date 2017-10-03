coordinates=de.tudarmstadt.ukp.dkpro.core_de.tudarmstadt.ukp.dkpro.core.io.pdf-asl_1.9.0-SNAPSHOT
className=de.tudarmstadt.ukp.dkpro.core.io.pdf.PdfReader
inD=../testInput/
otD=../testOutput_$className/

../Linux_runUIMA.sh $coordinates $className -input $inD -output $otD -PsubstitutionTableLocation='<built-in>' -PheadingType='<built-in>' -PparagraphType='<built-in>' -PstartPage='-1' -PendPage='-1' -Ppatterns='[+]**/*.pdf' -PuseDefaultExcludes='true' -Planguage='en' -PlogFreq='1'


