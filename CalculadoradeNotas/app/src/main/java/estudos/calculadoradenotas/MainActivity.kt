package estudos.calculadoradenotas
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calcularButton = bt_calcular
        val resultado = textView

        calcularButton.setOnClickListener {
            val nota1 = Integer.parseInt(textNota1.text.toString())
            val nota2 = Integer.parseInt(textNota2.text.toString())
            val faltas = Integer.parseInt(textFaltas.text.toString())

            val media = (nota1+nota2)/2

            if(media >= 6 && faltas <=10){
                resultado.setText("Aluno Aprovado!\nNota Final: ${media}\nFaltas: $faltas")
                resultado.setTextColor(Color.GREEN)
            } else {
                resultado.setText("Aluno Reprovado!\nNota Final: ${media}\nFaltas: $faltas")
                resultado.setTextColor(Color.RED)
            }
        }
    }
}