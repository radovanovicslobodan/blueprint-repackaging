package cucumber_blueprint.common.helpers.font_converter;


public class FontConverter {

    public String replaceSmallSh(String word){
        return word.replace("š", "\u0161");
    }

    public String replaceBigSh(String word){
        return word.replace("Š", "\u0160");
    }

    public String replaceSmallCh(String word){
        return word.replace("č", "\u010d");
    }

    public String replaceBigCh(String word){
        return word.replace("Č", "\u010c");
    }

    public String replaceSmallCj(String word){
        return word.replace("ć", "\u0107");
    }

    public String replaceBigCj(String word){
        return word.replace("Ć", "\u0106");
    }

    public String replaceSmallZ(String word){
        return word.replace("ž", "\u017e");
    }

    public String replaceBigZ(String word){
        return word.replace("Ž", "\u017d");
    }

    public String replaceSmallDj(String word){
        return word.replace("đ", "\u0111");
    }

    public String replaceBigDj(String word){
        return word.replace("Đ", "\u0110");
    }

    public String replaceLatinCharacters(String inputCharSet){
        String convertedCharacters = inputCharSet;

        if (inputCharSet.contains("š")) convertedCharacters = replaceSmallSh(inputCharSet);
        if (inputCharSet.contains("Š")) convertedCharacters = replaceBigSh(inputCharSet);
        if (inputCharSet.contains("č")) convertedCharacters = replaceSmallCh(inputCharSet);
        if (inputCharSet.contains("Č")) convertedCharacters = replaceBigCh(inputCharSet);
        if (inputCharSet.contains("ć")) convertedCharacters = replaceSmallCj(inputCharSet);
        if (inputCharSet.contains("Ć")) convertedCharacters = replaceBigCj(inputCharSet);
        if (inputCharSet.contains("ž")) convertedCharacters = replaceSmallZ(inputCharSet);
        if (inputCharSet.contains("Ž")) convertedCharacters = replaceBigZ(inputCharSet);
        if (inputCharSet.contains("đ")) convertedCharacters = replaceSmallDj(inputCharSet);
        if (inputCharSet.contains("Đ")) convertedCharacters = replaceBigDj(inputCharSet);

        return convertedCharacters;
    }

}
