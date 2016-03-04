with open(r"C:\Conrad\BARK\res\cmudict-en-us.dict", 'r') as fp:
    x = fp.read()

words = sorted(x.splitlines())

swords = []
for i in range(len(words)):
    swords.append(words[i][:words[i].find(' ')])
print(len(swords))

with open(r"C:\Conrad\BARK\res\engdictedit.txt", 'r') as tp:
    y = tp.read()
cwords = sorted(y.splitlines())

nwords = []
for i in range(len(cwords)):
    cword = cwords[i]
    print(cword)
    for j in range(len(swords)):
        sword = swords[i]
        if cword == sword:
            nwords.append(words[j])
nwords.sort()

with open(r"C:\Conrad\BARK\res\cmudictedit.dict", 'w+') as wf:
    wf.write('\n'.join(nwords))
