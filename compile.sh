#!/bin/bash
# Script de compilação do projeto GraphTasksTDEs

JAVAC="/c/Program Files/JetBrains/CLion 2026.1/jbr/bin/javac.exe"

echo "=== Compilando projeto GraphTasksTDEs ==="

# Compilar para o diretório output
$JAVAC -d output -sourcepath src \
    src/br/edu/grafo/model/*.java \
    src/br/edu/grafo/algorithm/*.java \
    src/br/edu/grafo/util/*.java \
    src/br/edu/grafo/app/*.java

if [ $? -eq 0 ]; then
    echo "✓ Compilação bem-sucedida!"
    echo ""
    echo "Para executar o menu interativo:"
    echo "  java -cp output br.edu.grafo.app.Main"
    echo ""
    echo "Para executar o exemplo:"
    echo "  java -cp output br.edu.grafo.app.ExemploGrafo"
else
    echo "✗ Erro na compilação"
    exit 1
fi
