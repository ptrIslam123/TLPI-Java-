package Lexer;

public enum TypeToken {

    /** ТИПЫ ДАННЫХ **/
    NumInt32, // число целое
    NumDouble64, // число с плавающей точкой

    Bool, // логический тип
    Str, // строковый тип
    Char, // символьный тип
    Void, // пустой тип

    /** МАТЕМАТИЧЕСКИЕ ОПЕРАНДЫ **/
    Add, // +
    Sub, // -
    Mult,  // *
    Div,  // /
    Equals, // =
    Lparen, // (
    Rparen, // )

    /** ЛОГИЧЕСКИЕ ОПЕРАНДЫ **/
    More, // >
    Less, // <
    MoreEq, // >=
    LessEq,  // <=
    Eq,   // ==
    NoEq,  // !=
    Disjunc, // !
    And, // &&
    Or, // ||


    /** КЛЮЧЕВЫЕ ОПЕРАНДЫ (ОПЕРАТОРЫ)**/
    Word, // слово
    Comma,  // ,
    Point,  // .
    Size,   // оператор вычисления размера объектов
    Len,   // длина строки
    ShapeLparen, // {
    ShapeRparen, // }
    L_SQUareParen, // [
    R_SQUareParen, // ]

    Incr, // ++
    Decr, // --
    IncrSomeNum, // +=
    DecrSomeNum, // -=

    If, // if
    Els, // else
    ElsIF, // els if

    While, // while
    For, // for
    Break, // break
    Continue, // continue
    in, // for i in 100 {...}
    Iterator,

    sys_write, // print функция
    sys_read,  // scan  функция

    Define_func, // определение функций (метода)
    ret, // return


    Str_cast, // приведение к типу string
    Int_cast,  // приведение к типу int
    Double_cast,  // приведение к типу  double
    Boll_cast,  // приведение к типу  bool

    Call, // вызов функциий

    Block,

    EOF, // конец файла
}
