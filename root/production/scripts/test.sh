jarFile=$( find ../../backend/target/asd -name "*.jar1" -print0 | xargs -r -0 ls -1 -t | head -) # get latest jar file

if ! [[ -z "$jarFile" ]]; then
    echo $jarFile
else
    echo "jar not found"
fi