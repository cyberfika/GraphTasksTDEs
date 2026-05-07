# AGENTS.md

> Status: Active
> Authority: Tier 2 - Agent Rules
> Last Updated: 2026-05-07
> Owner: Jafte Carneiro Fagundes da Silva

## Fonte Canonica

As regras completas para agentes estao em:

- `agents/agents.md`

Este arquivo existe na raiz porque o proprio guia de agentes espera encontrar `AGENTS.md` no diretorio principal do repositorio. Para evitar divergencia, mantenha `agents/agents.md` como o documento completo e use este arquivo como ponte.

## Excecao De Idioma Do Projeto

Por decisao do usuario em 2026-05-07:

- A documentacao do repositorio pode ficar em portugues.
- Comentarios e JavaDoc podem ficar em portugues.
- Codigo, nomes de arquivos, nomes de classes, nomes de metodos, pacotes e estrutura tecnica devem permanecer em ingles.

Essa excecao substitui a regra generica de idioma de `agents/agents.md` para este repositorio.

## Regras De Preservacao

- Nao alterar comportamento de runtime sem pedido explicito.
- Nao renomear classes, metodos, pacotes ou arquivos de codigo sem pedido explicito.
- Nao apagar dados `.bin`.
- Nao limpar `output/` ou remover artefatos compilados sem confirmacao.
- Nao executar `git commit`, `git push` ou operacoes remotas sem autorizacao.
