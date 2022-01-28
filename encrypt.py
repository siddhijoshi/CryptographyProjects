import getopt, sys

def usage():
    print("Usage: python encrypt.py -i [Input] [-k [Key]]")
    print("Input for -i: Path to the file to be encrypted.")
    print("      for -k: A seven digit number with non-repetitive digits.")
    print()
    print("Examples: python encrypt.py -i b.txt -k 5367241")
    print("          python encrypt.py -i b.txt")

argumentList = sys.argv[1:]

# Options
options = "hi:k:"
 
# Long options
long_options = ["Help", "Input = ", "Key ="]
 
try:
    arguments, values = getopt.getopt(argumentList, options, long_options)
     
    for currentArgument, currentValue in arguments:
 
        if currentArgument in ("-h", "--Help"):
            usage()
            sys.exit()
             
        elif currentArgument in ("-i", "--Input"):
            file_path = str(currentValue)
             
        elif currentArgument in ("-k", "--Key"):
            key = currentValue
             
except getopt.error as err:
    usage()
    print (str(err))

try:
    file1 = open(file_path,"r")
    text = file1.read().replace(" ", "")
    file1.close()
    #print(text)

    n = 7 
    #text = text + ("s" * len(text) % n)
    #print(len(text))

    chunks = [text[i:i+n] for i in range(0, len(text), n)]

    try:
        if key:
            print()
            print("The key you have provided : " + key)
            print()
    except Exception as e:
        from random import shuffle
        key = list("1234567")
        shuffle(key)
        key = ''.join(key)
        print()
        print("Random key : " + key)
        print()

    
    encrypted = ""

    #print("starting encryption")
    #print(chunks)
    for chunk in chunks:
        #print(chunk)
        enc = ['']*7
        i = 0
        for k in key:
            enc[int(k)-1] = chunk[i]
            #print(enc)
            i += 1
        
        str1 = ""
        str1 = str1.join(enc)
        #print(str1)
        encrypted += str1
    
    print(encrypted)
    print()
    f = open("CipherText.txt","w")
    f.write(encrypted)
    f.close()

except Exception as e:
    print("Please mention the input file. eg. \"python encrypt.py -i a.txt\"\n")
    usage()