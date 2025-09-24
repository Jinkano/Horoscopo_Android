package com.example.horoscopo_android

data class Horoscope (val id: String, val name: Int, val dates: Int, val icon: Int)
{
    companion object {
        fun getAll() : List<Horoscope> {
            return listOf(
                Horoscope("aries",R.string.txt_name_aries,R.string.txt_date_aries, R.drawable.ari),
            Horoscope("taurus",R.string.txt_name_taurus,R.string.txt_date_taurus, R.drawable.tau),
            Horoscope("gemini",R.string.txt_name_gemini,R.string.txt_date_gemini, R.drawable.gem),
            Horoscope("cancer",R.string.txt_name_cancer,R.string.txt_date_cancer, R.drawable.can),
            Horoscope("leo",R.string.txt_name_leo,R.string.txt_date_leo,R.drawable.leo),
            Horoscope("virgo",R.string.txt_name_virgo,R.string.txt_date_virgo, R.drawable.vir),
            Horoscope("libra",R.string.txt_name_libra,R.string.txt_date_libra, R.drawable.lib),
            Horoscope("scorpio",R.string.txt_name_scorpio,R.string.txt_date_scorpio, R.drawable.sco),
            Horoscope("sagittarius",R.string.txt_name_sagittarius,R.string.txt_date_sagittarius, R.drawable.sag),
            Horoscope("capricorn",R.string.txt_name_capricorn,R.string.txt_date_capricorn, R.drawable.cap),
            Horoscope("aquarius",R.string.txt_name_aquarius,R.string.txt_date_aquarius,R.drawable.aqu),
            Horoscope("pisces",R.string.txt_name_pisces,R.string.txt_date_pisces, R.drawable.pis)
            )
        }
    }
}