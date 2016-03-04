with open("2986.dic", 'r') as fp:
    x = fp.read()

lines = x.splitlines()

for i in range(len(lines)):
    line = lines[i]
    pos = line.find('\t')
    if pos == -1:
        pos = line.find(' ')
    lsub = line[:pos]
    llsub = line[pos:]
    lines[i] = lsub.lower() + llsub

with open("2986.dic", "w+") as op:
    op.write('\n'.join(lines))

