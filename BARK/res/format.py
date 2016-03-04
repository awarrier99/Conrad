with open(r"C:\Conrad\BARK\res\engdict.txt", 'r') as fp:
    x = fp.read()

words = sorted(x.splitlines())

def isPlural(x):
    if (x + 's') in words:
        return words.index(x + 's')
    if (x + "es") in words:
        return words.index(x + "es")

    return -1

def isYPlural(x):
    if (x[:-3] + 'y') in words:
        return words.index(x)

def isED(x):
    if (x + "ed") in words:
        return words.index(x + "ed")
    return -1

nwords = []
excinds = []
for i in range(len(words)):
    if len(words[i]) < 5:
        continue
    if isPlural(words[i]) != -1:
        excinds.append(isPlural(words[i]))
    if isYPlural(words[i]) != -1:
        excinds.append(isYPlural(words[i]))
    if isED(words[i]) != -1:
        excinds.append(isED(words[i]))
    if i in excinds:
        continue
    
    nwords.append(words[i])

nwords.sort()

with open(r"C:\Conrad\BARK\res\engdictedit.txt", 'w+') as wf:
    wf.write('\n'.join(nwords))
