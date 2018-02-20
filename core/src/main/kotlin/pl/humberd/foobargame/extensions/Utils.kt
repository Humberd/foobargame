package pl.humberd.foobargame.extensions

//fun <T : Observable<*>> T.observeOnGdx() {
//    this.observeOn(Gdx.app as LwjglApplication)
//}

fun Float.toPercentString(): String {
    return Math.ceil((this * 100).toDouble()).toInt().toString() + "%"
}