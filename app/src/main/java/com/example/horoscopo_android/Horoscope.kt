package com.example.horoscopo_android

data class Horoscope (val id: String, val name: Int, val dates: Int, val icon: Int)
{
    companion object {
        fun getAll() : List<Horoscope> {
            return listOf(
                Horoscope("aries",R.string.txt_name_aries,R.string.txt_date_aries, R.drawable.ic_aries_background),
            Horoscope("taurus",R.string.txt_name_taurus,R.string.txt_date_taurus, R.drawable.taurus),
            Horoscope("gemini",R.string.txt_name_gemini,R.string.txt_date_gemini, R.drawable.ic_gemini_background),
            Horoscope("cancer",R.string.txt_name_cancer,R.string.txt_date_cancer, R.drawable.ic_cancer_background),
            Horoscope("leo",R.string.txt_name_leo,R.string.txt_date_leo,R.drawable.ic_leo_background),
            Horoscope("virgo",R.string.txt_name_virgo,R.string.txt_date_virgo, R.drawable.ic_virgo_background),
            Horoscope("libra",R.string.txt_name_libra,R.string.txt_date_libra, R.drawable.ic_libra_background),
            Horoscope("scorpio",R.string.txt_name_scorpio,R.string.txt_date_scorpio, R.drawable.ic_scorpio_background),
            Horoscope("sagittarius",R.string.txt_name_sagittarius,R.string.txt_date_sagittarius, R.drawable.ic_saguittarius_background),
            Horoscope("capricorn",R.string.txt_name_capricorn,R.string.txt_date_capricorn, R.drawable.ic_capricorn_background),
            Horoscope("aquarius",R.string.txt_name_aquarius,R.string.txt_date_aquarius,R.drawable.ic_aquarius_background),
            Horoscope("pisces",R.string.txt_name_pisces,R.string.txt_date_pisces, R.drawable.ic_pisces_background)
            )
        }
    }
}