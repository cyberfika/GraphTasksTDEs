#!/bin/bash
# Script de compilacao do projeto GraphTasksTDEs.

JAVAC="/c/Program Files/JetBrains/CLion 2026.1/jbr/bin/javac.exe"

echo "=== Compilando projeto GraphTasksTDEs ==="

# Gera bytecode compativel com Java 8, conforme documentacao do projeto.
$JAVAC --release 8 -d output -sourcepath src \
    src/br/edu/grafo/model/*.java \
    src/br/edu/grafo/algorithm/*.java \
    src/br/edu/grafo/util/*.java \
    src/br/edu/grafo/application/*.java \
    src/br/edu/grafo/interfaces/*.java \
    src/br/edu/grafo/app/*.java

if [ $? -eq 0 ]; then
    echo "Compilacao bem-sucedida!"
    echo ""
    echo "Para executar o menu interativo:"
    echo "  java -cp output br.edu.grafo.app.Main"
    echo ""
    echo "Para executar o exemplo:"
    echo "  java -cp output br.edu.grafo.app.ExampleGraph"
else
    echo "Erro na compilacao"
    exit 1
fi
