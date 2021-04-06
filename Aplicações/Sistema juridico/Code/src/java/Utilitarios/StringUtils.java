package Utilitarios;

import Model.Causevariable;
import Model.Client;
import Model.Company;
import java.util.List;

public class StringUtils {

    public static int countOcurrences(String toAnalyse, String toFind) {
        int lastIndex = 0;
        int count = 0;

        while (lastIndex != -1) {

            lastIndex = toAnalyse.indexOf(toFind, lastIndex);

            if (lastIndex != -1) {
                count++;
                lastIndex += toFind.length();
            }
        }
        return count;
    }

    public static String replaceContentOnCorpus(String corpus, String toFindOnCorpus, String contentToReplace) {
        if (corpus != null && toFindOnCorpus != null && contentToReplace != null) {
            return corpus.replace(toFindOnCorpus, contentToReplace);
        }
        return null;
    }

    public static String replaceModelOnCorpus(String corpus, String contentToReplace) {
        corpus = corpus.replace("!#inserirModelo#!", contentToReplace);
        return corpus;
    }

    public static String replaceFieldsInPart(Client toUse, String partToReplace) {
        if (toUse != null && partToReplace != null) {
            if (partToReplace.contains("!#cpf#!")) {
                partToReplace = partToReplace.replace("!#cpf#!", toUse.getCpfRegister());
            }
            if (partToReplace.contains("!#nomeCompleto#!")) {
                partToReplace = partToReplace.replace("!#nomeCompleto#!", toUse.getFullName());
            }
            if (partToReplace.contains("!#nacionalidade#!")) {
                partToReplace = partToReplace.replace("!#nacionalidade#!", toUse.getNationality());
            }
            if (partToReplace.contains("!#dataDeNascimento#!")) {
                partToReplace = partToReplace.replace("!#dataDeNascimento#!", toUse.getBirthDate().toString());
            }
            if (partToReplace.contains("!#estadoCivil#!")) {
                partToReplace = partToReplace.replace("!#estadoCivil#!", toUse.getMaritalStatus());
            }
            if (partToReplace.contains("!#ocupacao#!")) {
                partToReplace = partToReplace.replace("!#ocupacao#!", toUse.getJob());
            }
            if (partToReplace.contains("!#ctps#!")) {
                partToReplace = partToReplace.replace("!#ctps#!", toUse.getCtps());
            }
            if (partToReplace.contains("!#rg#!")) {
                partToReplace = partToReplace.replace("!#rg#!", toUse.getGeneralRegister());
            }
            if (partToReplace.contains("!#rgEstado#!")) {
                partToReplace = partToReplace.replace("!#rgEstado#!", toUse.getRgState());
            }
            if (partToReplace.contains("!#rgEmissor#!")) {
                partToReplace = partToReplace.replace("!#rgEmissor#!", toUse.getRgEmiter());
            }
            if (partToReplace.contains("!#bairro#!")) {
                partToReplace = partToReplace.replace("!#bairro#!", toUse.getDistrict());
            }
            if (partToReplace.contains("!#cep#!")) {
                partToReplace = partToReplace.replace("!#cep#!", toUse.getCep());
            }
            if (partToReplace.contains("!#endereco1#!")) {
                partToReplace = partToReplace.replace("!#endereco1#!", toUse.getAddress1());
            }
            if (partToReplace.contains("!#endereco2#!")) {
                partToReplace = partToReplace.replace("!#endereco2#!", toUse.getAddress2());
            }
            if (partToReplace.contains("!#cidade#!")) {
                partToReplace = partToReplace.replace("!#cidade#!", toUse.getCity());
            }
            if (partToReplace.contains("!#estado#!")) {
                partToReplace = partToReplace.replace("!#estado#!", toUse.getState());
            }
            if (partToReplace.contains("!#comarca#!")) {
                partToReplace = partToReplace.replace("!#comarca#!", toUse.getVotersTitle());
            }
            if (partToReplace.contains("!#tituloDeEleitor#!")) {
                partToReplace = partToReplace.replace("!#tituloDeEleitor#!", toUse.getVotersTitle());
            }
            if (partToReplace.contains("!#telefone#!")) {
                partToReplace = partToReplace.replace("!#telefone#!", toUse.getPhone());
            }
            if (partToReplace.contains("!#email#!")) {
                partToReplace = partToReplace.replace("!#email#!", toUse.getEmail());
            }
        }
        return partToReplace;

    }

    public static String replaceFieldsInPart(Company toUse, String partToReplace) {
        if (toUse != null && partToReplace != null) {
            if (partToReplace.contains("!#cnpj#!")) {
                partToReplace = partToReplace.replace("!#cnpj#!", toUse.getCnpj());
            }
            if (partToReplace.contains("!#nome#!")) {
                partToReplace = partToReplace.replace("!#nome#!", toUse.getName());
            }
            if (partToReplace.contains("!#pais#!")) {
                partToReplace = partToReplace.replace("!#pais#!", toUse.getCountry());
            }
            if (partToReplace.contains("!#cep#!")) {
                partToReplace = partToReplace.replace("!#cep#!", toUse.getCep());
            }
            if (partToReplace.contains("!#estado#!")) {
                partToReplace = partToReplace.replace("!#estado#!", toUse.getState());
            }
            if (partToReplace.contains("!#cidade#!")) {
                partToReplace = partToReplace.replace("!#cidade#!", toUse.getCity());
            }
            if (partToReplace.contains("!#endereco1#!")) {
                partToReplace = partToReplace.replace("!#endereco1#!", toUse.getAddress1());
            }
            if (partToReplace.contains("!#endereco2#!")) {
                partToReplace = partToReplace.replace("!#endereco2#!", toUse.getAddress2());
            }
            if (partToReplace.contains("!#telefone#!")) {
                partToReplace = partToReplace.replace("!#telefone#!", toUse.getPhone());
            }
            if (partToReplace.contains("!#email#!")) {
                partToReplace = partToReplace.replace("!#email#!", toUse.getEmail());
            }
        }
        return partToReplace;

    }

    public static String replaceUserVariablesOnCorpus(String corpus, List<Causevariable> userVariablesToReplaceOnCorpus) {
        String toReturn = corpus;
        if (corpus != null && userVariablesToReplaceOnCorpus != null && userVariablesToReplaceOnCorpus.size() > 0) {
            for (Causevariable var : userVariablesToReplaceOnCorpus) {
                if (var.getVariableName().contains("!$")) {
                     toReturn = toReturn.replace(var.getVariableName(), EscreveNumeroPorExtenso.valorPorExtenso(Double.parseDouble(var.getVariableContent())));
                } else {
                    toReturn = toReturn.replace(var.getVariableName(), var.getVariableContent());
                }
            }
        }
        if (toReturn.contains("!#dataAtualExtenso#!")) {
            toReturn = corpus.replace("!#dataAtualExtenso#!", DateUtils.getActualDateComplete());
        }
        if (toReturn.contains("!#dataAtual#!")) {
            toReturn = corpus.replace("!#dataAtual#!", DateUtils.getActualDateShort());
        }

        return toReturn;
    }

}
