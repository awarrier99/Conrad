with open(r"C:\Conrad\BARK\res\engdict.txt", 'r') as fp:
    x = fp.read()

words = sorted(x.splitlines())
nwords = []
for i in range(len(words)):
    if len(words[i]) < 5:
        continue
    if words[i] == (words[i - 1] + 's'):
        continue
    nwords.append(words[i])

nwords.sort()

with open(r"C:\Conrad\BARK\res\engdictedit.txt", 'w+') as wf:
    wf.write('\n'.join(nwords))
