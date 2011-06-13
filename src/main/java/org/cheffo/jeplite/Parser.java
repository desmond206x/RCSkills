/* Generated By:JJTree&JavaCC: Do not edit this line. Parser.java */
package org.cheffo.jeplite;
import java.util.*;
import org.cheffo.jeplite.function.*;
import org.cheffo.jeplite.function.PostfixMathCommand.*;

class Parser/*@bgen(jjtree)*/implements ParserTreeConstants, ParserConstants {/*@bgen(jjtree)*/
  protected JJTParserState jjtree = new JJTParserState();
  private HashMap<?, ?> symTab;
  private HashMap<?, ?> funTab;
  private Vector<String> errorList;
  public SimpleNode parseStream(java.io.Reader stream, HashMap<?, ?> symTab_in, HashMap<?, ?> funTab_in) throws ParseException
  {
          ReInit(stream);
          symTab = symTab_in;
          funTab = funTab_in;
          errorList = new Vector<String>();
          SimpleNode n = Start();
          return n.jjtGetChild(0);
  }

  private void addToErrorList(String errorStr)
  {
          errorList.addElement(errorStr);
  }

  public Vector<String> getErrorList()
  {
          return errorList;
  }

  final public SimpleNode Start() throws ParseException {
  SimpleNode jjtn000 = new SimpleNode(JJTSTART);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case INTEGER_LITERAL:
      case FLOATING_POINT_LITERAL:
      case STRING_LITERAL:
      case IDENTIFIER:
      case 12:
      case 19:
      case 20:
      case 25:
      case 28:
        Expression();
        jj_consume_token(0);
        jjtree.closeNodeScope(jjtn000, true);
        jjtc000 = false;
        return jjtn000;
      case 0:
        jj_consume_token(0);
    jjtree.closeNodeScope(jjtn000, true);
    jjtc000 = false;
    throw new ParseException(token, "No expression entered");
      default:
        jj_la1[0] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } catch (Throwable jjte000) {
      if (jjtc000) {
        jjtree.clearNodeScope(jjtn000);
        jjtc000 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte000 instanceof RuntimeException) {
        {throw (RuntimeException)jjte000;}
      }
      if (jjte000 instanceof ParseException) {
        throw (ParseException)jjte000;
      }
      throw (Error)jjte000;
    } finally {
      if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, true);
      }
    }
  }

  final public void Expression() throws ParseException {
    LogicalExpression();
  }

  final public void LogicalExpression() throws ParseException {
    NotExpression();
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 10:
      case 11:
        ;
        break;
      default:
        jj_la1[1] = jj_gen;
        break label_1;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 10:
            ASTFunNode jjtn001 = new ASTFunNode(JJTFUNNODE);
            boolean jjtc001 = true;
            jjtree.openNodeScope(jjtn001);
        try {
          jj_consume_token(10);
          NotExpression();
          jjtree.closeNodeScope(jjtn001,  2);
          jjtc001 = false;
          jjtn001.setFunction("&&", new Logical(0));
        } catch (Throwable jjte001) {
            if (jjtc001) {
              jjtree.clearNodeScope(jjtn001);
              jjtc001 = false;
            } else {
              jjtree.popNode();
            }
            if (jjte001 instanceof RuntimeException) {
              throw (RuntimeException)jjte001;
            }
            if (jjte001 instanceof ParseException) {
              throw (ParseException)jjte001;
            }
            throw (Error)jjte001;
        } finally {
            if (jjtc001) {
              jjtree.closeNodeScope(jjtn001,  2);
            }
        }
        break;
      case 11:
            ASTFunNode jjtn002 = new ASTFunNode(JJTFUNNODE);
            boolean jjtc002 = true;
            jjtree.openNodeScope(jjtn002);
        try {
          jj_consume_token(11);
          NotExpression();
              jjtree.closeNodeScope(jjtn002,  2);
              jjtc002 = false;
                jjtn002.setFunction("||", new Logical(1));
        } catch (Throwable jjte002) {
            if (jjtc002) {
              jjtree.clearNodeScope(jjtn002);
              jjtc002 = false;
            } else {
              jjtree.popNode();
            }
            if (jjte002 instanceof RuntimeException) {
              throw (RuntimeException)jjte002;
            }
            if (jjte002 instanceof ParseException) {
              {if (true) throw (ParseException)jjte002;}
            }
            {if (true) throw (Error)jjte002;}
        } finally {
            if (jjtc002) {
              jjtree.closeNodeScope(jjtn002,  2);
            }
        }
        break;
      default:
        jj_la1[2] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
  }

  final public void NotExpression() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INTEGER_LITERAL:
    case FLOATING_POINT_LITERAL:
    case STRING_LITERAL:
    case IDENTIFIER:
    case 19:
    case 20:
    case 25:
    case 28:
      RelationalExpression();
      break;
    case 12:
    ASTFunNode jjtn001 = new ASTFunNode(JJTFUNNODE);
    boolean jjtc001 = true;
    jjtree.openNodeScope(jjtn001);
      try {
        jj_consume_token(12);
        RelationalExpression();
      jjtree.closeNodeScope(jjtn001,  1);
      jjtc001 = false;
          jjtn001.setFunction("!", new Not());
      } catch (Throwable jjte001) {
    if (jjtc001) {
      jjtree.clearNodeScope(jjtn001);
      jjtc001 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte001 instanceof RuntimeException) {
      throw (RuntimeException)jjte001;
    }
    if (jjte001 instanceof ParseException) {
      throw (ParseException)jjte001;
    }
    throw (Error)jjte001;
      } finally {
    if (jjtc001) {
      jjtree.closeNodeScope(jjtn001,  1);
    }
      }
      break;
    default:
      jj_la1[3] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void RelationalExpression() throws ParseException {
    OrEqualExpression();
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 13:
      case 14:
        ;
        break;
      default:
        jj_la1[4] = jj_gen;
        break label_2;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 13:
      ASTFunNode jjtn001 = new ASTFunNode(JJTFUNNODE);
      boolean jjtc001 = true;
      jjtree.openNodeScope(jjtn001);
        try {
          jj_consume_token(13);
          OrEqualExpression();
          jjtree.closeNodeScope(jjtn001,  2);
          jjtc001 = false;
          jjtn001.setFunction("<", new Comparative(0));
        } catch (Throwable jjte001) {
          if (jjtc001) {
        jjtree.clearNodeScope(jjtn001);
        jjtc001 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte001 instanceof RuntimeException) {
        throw (RuntimeException)jjte001;
      }
      if (jjte001 instanceof ParseException) {
        throw (ParseException)jjte001;
      }
      throw (Error)jjte001;
        } finally {
        if (jjtc001) {
        jjtree.closeNodeScope(jjtn001,  2);
      }
        }
        break;
      case 14:
      ASTFunNode jjtn002 = new ASTFunNode(JJTFUNNODE);
      boolean jjtc002 = true;
      jjtree.openNodeScope(jjtn002);
        try {
          jj_consume_token(14);
          OrEqualExpression();
        jjtree.closeNodeScope(jjtn002,  2);
        jjtc002 = false;
        jjtn002.setFunction(">", new Comparative(1));
        } catch (Throwable jjte002) {
      if (jjtc002) {
        jjtree.clearNodeScope(jjtn002);
        jjtc002 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte002 instanceof RuntimeException) {
        throw (RuntimeException)jjte002;
      }
      if (jjte002 instanceof ParseException) {
        throw (ParseException)jjte002;
      }
      throw (Error)jjte002;
        } finally {
      if (jjtc002) {
        jjtree.closeNodeScope(jjtn002,  2);
      }
        }
        break;
      default:
        jj_la1[5] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
  }

  final public void OrEqualExpression() throws ParseException {
    EqualExpression();
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 15:
      case 16:
        ;
        break;
      default:
        jj_la1[6] = jj_gen;

        break label_3;

      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 15:
      ASTFunNode jjtn001 = new ASTFunNode(JJTFUNNODE);
      boolean jjtc001 = true;
      jjtree.openNodeScope(jjtn001);
        try {
          jj_consume_token(15);
          EqualExpression();
        jjtree.closeNodeScope(jjtn001,  2);
        jjtc001 = false;
            jjtn001.setFunction("<=", new Comparative(2));
        } catch (Throwable jjte001) {
      if (jjtc001) {
        jjtree.clearNodeScope(jjtn001);
        jjtc001 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte001 instanceof RuntimeException) {
        throw (RuntimeException)jjte001;
      }
      if (jjte001 instanceof ParseException) {
        throw (ParseException)jjte001;
      }
      throw (Error)jjte001;
        } finally {
      if (jjtc001) {
        jjtree.closeNodeScope(jjtn001,  2);
      }
        }
        break;
      case 16:
      ASTFunNode jjtn002 = new ASTFunNode(JJTFUNNODE);
      boolean jjtc002 = true;
      jjtree.openNodeScope(jjtn002);
        try {
          jj_consume_token(16);
          EqualExpression();
        jjtree.closeNodeScope(jjtn002,  2);
        jjtc002 = false;
        jjtn002.setFunction(">=", new Comparative(3));
        } catch (Throwable jjte002) {
      if (jjtc002) {
        jjtree.clearNodeScope(jjtn002);
        jjtc002 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte002 instanceof RuntimeException) {
        throw (RuntimeException)jjte002;
      }
      if (jjte002 instanceof ParseException) {
        {if (true) throw (ParseException)jjte002;}
      }
      {if (true) throw (Error)jjte002;}
        } finally {
      if (jjtc002) {
        jjtree.closeNodeScope(jjtn002,  2);
      }
        }
        break;
      default:
        jj_la1[7] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
  }

  final public void EqualExpression() throws ParseException {
    AdditiveExpression();
    label_4:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 17:
      case 18:
        ;
        break;
      default:
        jj_la1[8] = jj_gen;
        break label_4;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 17:
            ASTFunNode jjtn001 = new ASTFunNode(JJTFUNNODE);
            boolean jjtc001 = true;
            jjtree.openNodeScope(jjtn001);
        try {
          jj_consume_token(17);
          AdditiveExpression();
              jjtree.closeNodeScope(jjtn001,  2);
              jjtc001 = false;
            jjtn001.setFunction("!=", new Comparative(4));
        } catch (Throwable jjte001) {
            if (jjtc001) {
              jjtree.clearNodeScope(jjtn001);
              jjtc001 = false;
            } else {
              jjtree.popNode();
            }
            if (jjte001 instanceof RuntimeException) {
              throw (RuntimeException)jjte001;
            }
            if (jjte001 instanceof ParseException) {
              throw (ParseException)jjte001;
            }
            throw (Error)jjte001;
        } finally {
            if (jjtc001) {
           jjtree.closeNodeScope(jjtn001,  2);
            }
        }
        break;
      case 18:
            ASTFunNode jjtn002 = new ASTFunNode(JJTFUNNODE);
            boolean jjtc002 = true;
            jjtree.openNodeScope(jjtn002);
        try {
          jj_consume_token(18);
          AdditiveExpression();
              jjtree.closeNodeScope(jjtn002,  2);
              jjtc002 = false;
              jjtn002.setFunction("==", new Comparative(5));
        } catch (Throwable jjte002) {
            if (jjtc002) {
              jjtree.clearNodeScope(jjtn002);
              jjtc002 = false;
            } else {
              jjtree.popNode();
            }
            if (jjte002 instanceof RuntimeException) {
              {if (true) throw (RuntimeException)jjte002;}
            }
            if (jjte002 instanceof ParseException) {
              throw (ParseException)jjte002;
            }
            throw (Error)jjte002;
        } finally {
            if (jjtc002) {
              jjtree.closeNodeScope(jjtn002,  2);
            }
        }
        break;
      default:
        jj_la1[9] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
  }

  final public void AdditiveExpression() throws ParseException {
    MultiplicativeExpression();
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 19:
      case 20:
        ;
        break;
      default:
        jj_la1[10] = jj_gen;
        break label_5;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 19:
      ASTFunNode jjtn001 = new ASTFunNode(JJTFUNNODE);
      boolean jjtc001 = true;
      jjtree.openNodeScope(jjtn001);
        try {
          jj_consume_token(19);
          MultiplicativeExpression();
        jjtree.closeNodeScope(jjtn001,  2);
        jjtc001 = false;
        jjtn001.setFunction("+", PostfixMathCommand.ADD);
        } catch (Throwable jjte001) {
      if (jjtc001) {
        jjtree.clearNodeScope(jjtn001);
        jjtc001 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte001 instanceof RuntimeException) {
        throw (RuntimeException)jjte001;
      }
      if (jjte001 instanceof ParseException) {
        throw (ParseException)jjte001;
      }
      throw (Error)jjte001;
        } finally {
      if (jjtc001) {
        jjtree.closeNodeScope(jjtn001,  2);
      }
        }
        break;
      case 20:
      ASTFunNode jjtn002 = new ASTFunNode(JJTFUNNODE);
      boolean jjtc002 = true;
      jjtree.openNodeScope(jjtn002);
        try {
          jj_consume_token(20);
          MultiplicativeExpression();
        jjtree.closeNodeScope(jjtn002,  2);
        jjtc002 = false;
        jjtn002.setFunction("-", new Subtract());
        } catch (Throwable jjte002) {
      if (jjtc002) {
        jjtree.clearNodeScope(jjtn002);
        jjtc002 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte002 instanceof RuntimeException) {
        throw (RuntimeException)jjte002;
      }
      if (jjte002 instanceof ParseException) {
        throw (ParseException)jjte002;
      }
      throw (Error)jjte002;
        } finally {
      if (jjtc002) {
        jjtree.closeNodeScope(jjtn002,  2);
      }
        }
        break;
      default:
        jj_la1[11] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
  }

  final public void MultiplicativeExpression() throws ParseException {
    DivisionExpression();
    label_6:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 21:
        ;
        break;
      default:
        jj_la1[12] = jj_gen;
        break label_6;
      }
      ASTFunNode jjtn001 = new ASTFunNode(JJTFUNNODE);
      boolean jjtc001 = true;
      jjtree.openNodeScope(jjtn001);
      try {
        jj_consume_token(21);
        DivisionExpression();
        jjtree.closeNodeScope(jjtn001,  2);
        jjtc001 = false;
        jjtn001.setFunction("*", PostfixMathCommand.MULTIPLY);
      } catch (Throwable jjte001) {
      if (jjtc001) {
        jjtree.clearNodeScope(jjtn001);
        jjtc001 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte001 instanceof RuntimeException) {
        throw (RuntimeException)jjte001;
      }
      if (jjte001 instanceof ParseException) {
        throw (ParseException)jjte001;
      }
      {if (true) throw (Error)jjte001;}
      } finally {
      if (jjtc001) {
        jjtree.closeNodeScope(jjtn001,  2);
      }
      }
    }
  }

  final public void DivisionExpression() throws ParseException {
    ModulusExpression();
    label_7:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 22:
        ;
        break;
      default:
        jj_la1[13] = jj_gen;
        break label_7;
      }
      ASTFunNode jjtn001 = new ASTFunNode(JJTFUNNODE);
      boolean jjtc001 = true;
      jjtree.openNodeScope(jjtn001);
     try {
        jj_consume_token(22);
        ModulusExpression();
        jjtree.closeNodeScope(jjtn001,  2);
        jjtc001 = false;
        jjtn001.setFunction("/", PostfixMathCommand.DIVIDE);
      } catch (Throwable jjte001) {
      if (jjtc001) {
        jjtree.clearNodeScope(jjtn001);
        jjtc001 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte001 instanceof RuntimeException) {
        throw (RuntimeException)jjte001;
      }
      if (jjte001 instanceof ParseException) {
        {if (true) throw (ParseException)jjte001;}
      }
      {if (true) throw (Error)jjte001;}
      } finally {
      if (jjtc001) {
        jjtree.closeNodeScope(jjtn001,  2);
      }
      }
    }
  }

  final public void ModulusExpression() throws ParseException {
    UnaryExpression();
    label_8:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 23:
        ;
        break;
      default:
        jj_la1[14] = jj_gen;
        break label_8;
      }
      ASTFunNode jjtn001 = new ASTFunNode(JJTFUNNODE);
      boolean jjtc001 = true;
      jjtree.openNodeScope(jjtn001);
      try {
        jj_consume_token(23);
        UnaryExpression();
        jjtree.closeNodeScope(jjtn001,  2);
        jjtc001 = false;
        jjtn001.setFunction("%", new Modulus());
      } catch (Throwable jjte001) {
      if (jjtc001) {
        jjtree.clearNodeScope(jjtn001);
        jjtc001 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte001 instanceof RuntimeException) {
        throw (RuntimeException)jjte001;
      }
      if (jjte001 instanceof ParseException) {
        throw (ParseException)jjte001;
      }
      throw (Error)jjte001;
      } finally {
      if (jjtc001) {
        jjtree.closeNodeScope(jjtn001,  2);
      }
      }
    }
  }

  final public void UnaryExpression() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 19:
      jj_consume_token(19);
      UnaryExpression();
      break;
    case 20:
    ASTFunNode jjtn001 = new ASTFunNode(JJTFUNNODE);
    boolean jjtc001 = true;
    jjtree.openNodeScope(jjtn001);
      try {
        jj_consume_token(20);
        UnaryExpression();
      jjtree.closeNodeScope(jjtn001,  1);
      jjtc001 = false;
          jjtn001.setFunction("-", PostfixMathCommand.UMINUS);
      } catch (Throwable jjte001) {
    if (jjtc001) {
      jjtree.clearNodeScope(jjtn001);
      jjtc001 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte001 instanceof RuntimeException) {
      throw (RuntimeException)jjte001;
    }
    if (jjte001 instanceof ParseException) {
      throw (ParseException)jjte001;
    }
    throw (Error)jjte001;
      } finally {
    if (jjtc001) {
      jjtree.closeNodeScope(jjtn001,  1);
    }
      }
     break;
    case INTEGER_LITERAL:
    case FLOATING_POINT_LITERAL:
    case STRING_LITERAL:
    case IDENTIFIER:
    case 25:
    case 28:
      PowerExpression();
      break;
    default:
      jj_la1[15] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void PowerExpression() throws ParseException {
    UnaryExpressionNotPlusMinus();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 24:
    ASTFunNode jjtn001 = new ASTFunNode(JJTFUNNODE);
    boolean jjtc001 = true;
    jjtree.openNodeScope(jjtn001);
      try {
        jj_consume_token(24);
        UnaryExpression();
      jjtree.closeNodeScope(jjtn001,  2);
      jjtc001 = false;
      jjtn001.setFunction("^", new Power());
      } catch (Throwable jjte001) {
    if (jjtc001) {
      jjtree.clearNodeScope(jjtn001);
      jjtc001 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte001 instanceof RuntimeException) {
      throw (RuntimeException)jjte001;
    }
    if (jjte001 instanceof ParseException) {
      throw (ParseException)jjte001;
    }
    throw (Error)jjte001;
      } finally {
    if (jjtc001) {
      jjtree.closeNodeScope(jjtn001,  2);
    }
      }
      break;
    default:
      jj_la1[16] = jj_gen;
      ;
    }
  }

  final public void UnaryExpressionNotPlusMinus() throws ParseException {
  int reqArguments = 0;
  boolean isSymbol = false;
  boolean isFunction = false;
  String identString = "";
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INTEGER_LITERAL:
    case FLOATING_POINT_LITERAL:
    case STRING_LITERAL:
    case 28:
      AnyConstant();
      break;
    case 25:
      jj_consume_token(25);
      Expression();
      jj_consume_token(26);
      break;
    case IDENTIFIER:
      if (jj_2_1(2)) {
      ASTFunNode jjtn001 = new ASTFunNode(JJTFUNNODE);
      boolean jjtc001 = true;
      jjtree.openNodeScope(jjtn001);
        try {
          identString = Identifier();
        if (funTab.containsKey(identString))
        {
          reqArguments = ((PostfixMathCommand)funTab.get(identString)).getNumberOfParameters();
          jjtn001.setFunction(identString, (PostfixMathCommand)funTab.get(identString));
        }
        else
        {
          addToErrorList("Unrecognized function \"" + identString + "\"");
        }
          jj_consume_token(25);
          ArgumentList(reqArguments, identString);
          jj_consume_token(26);
        jjtree.closeNodeScope(jjtn001, true);
        jjtc001 = false;
        } catch (Throwable jjte001) {
      if (jjtc001) {
        jjtree.clearNodeScope(jjtn001);
        jjtc001 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte001 instanceof RuntimeException) {
        throw (RuntimeException)jjte001;
      }
      if (jjte001 instanceof ParseException) {
        throw (ParseException)jjte001;
      }
      throw (Error)jjte001;
        } finally {
      if (jjtc001) {
        jjtree.closeNodeScope(jjtn001, true);
      }
        }
      } else {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case IDENTIFIER:
	boolean jjtc002 = true;
	identString = Identifier();
	ASTVarNode jjtn002 = (ASTVarNode)symTab.get(identString);
	if(jjtn002==null) {
	  addToErrorList("Unrecognized symbol \"" + identString +"\"");
	  jjtn002 = new ASTVarNode(JJTVARNODE);
	  return;
	}
        try {
	    jjtree.openNodeScope(jjtn002);
	    jjtree.closeNodeScope(jjtn002, true);
	    jjtc002 = false;
	    if (symTab.containsKey(identString))
    {
      jjtn002.setName(identString);
    }
    else
    {
      addToErrorList("Unrecognized symbol \"" + identString +"\"");
    }
         } catch (Throwable jjte002) {
     if (jjtc002) {
        jjtree.clearNodeScope(jjtn002);
        jjtc002 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte002 instanceof RuntimeException) {
        throw (RuntimeException)jjte002;
      }
      if (jjte002 instanceof ParseException) {
        throw (ParseException)jjte002;
      }
      throw (Error)jjte002;
          } finally {
      if (jjtc002) {
        jjtree.closeNodeScope(jjtn002, true);
      }
          }
          break;
        default:
          jj_la1[17] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
      break;
    default:
      jj_la1[18] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void ArgumentList(int reqArguments, String functionName) throws ParseException {
        int count = 0;
        String errorStr = "";
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INTEGER_LITERAL:
    case FLOATING_POINT_LITERAL:
    case STRING_LITERAL:
    case IDENTIFIER:
    case 12:
    case 19:
    case 20:
    case 25:
    case 28:
      Expression();
                       count++;
      label_9:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 27:
          ;
          break;
        default:
          jj_la1[19] = jj_gen;
          break label_9;
        }
        jj_consume_token(27);
        Expression();
                               count++;
      }
      break;
    default:
      jj_la1[20] = jj_gen;
      ;
    }
        if (reqArguments != count && reqArguments != -1)
                {
                        errorStr = "Function \"" + functionName +"\" requires " + reqArguments + " parameter";
                        if (reqArguments!=1) errorStr += "s";
                        addToErrorList(errorStr);
                }
  }

  final public String Identifier() throws ParseException {
  Token t;
    t = jj_consume_token(IDENTIFIER);
    return t.image;
  }

  final public void AnyConstant() throws ParseException {
        ASTConstant jjtn000 = new ASTConstant(JJTCONSTANT);
        boolean jjtc000 = true;
        jjtree.openNodeScope(jjtn000);Token t;
        double value;
        Vector<?> array;
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case INTEGER_LITERAL:
      case FLOATING_POINT_LITERAL:
        value = RealConstant();
          jjtree.closeNodeScope(jjtn000, true);
          jjtc000 = false;
                jjtn000.setValue(value);
        break;
      default:
        jj_la1[21] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            throw (RuntimeException)jjte000;
          }
          if (jjte000 instanceof ParseException) {
            throw (ParseException)jjte000;
          }
          throw (Error)jjte000;
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
  }


  final public double RealConstant() throws ParseException {
  Token t;
  double value;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INTEGER_LITERAL:
      t = jj_consume_token(INTEGER_LITERAL);
      break;
    case FLOATING_POINT_LITERAL:
      t = jj_consume_token(FLOATING_POINT_LITERAL);
      break;
    default:
      jj_la1[23] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
        try
        {
          Double number = new Double(t.image);
          value = number.doubleValue();
        } catch (Exception e)
        {
          value = 0;
          addToErrorList("Can't parse \"" + t.image + "\"");
        }
        return value;
  }

  final private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    boolean retval = !jj_3_1();
    jj_save(0, xla);
    return retval;
  }

  final private boolean jj_3_1() {
    if (jj_3R_11()) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    if (jj_scan_token(25)) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    return false;
  }

  final private boolean jj_3R_11() {
    if (jj_scan_token(IDENTIFIER)) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    return false;
  }

  public ParserTokenManager token_source;
  ASCII_CharStream jj_input_stream;
  public Token token, jj_nt;
  private int jj_ntk;
  private Token jj_scanpos, jj_lastpos;
  private int jj_la;
  public boolean lookingAhead = false;
  private boolean jj_semLA;
  private int jj_gen;
  final private int[] jj_la1 = new int[24];
  final private int[] jj_la1_0 = {0x121810d5,0xc00,0xc00,0x121810d4,0x6000,0x6000,0x18000,0x18000,0x60000,0x60000,0x180000,0x180000,0x200000,0x400000,0x800000,0x121800d4,0x1000000,0x80,0x120000d4,0x8000000,0x121810d4,0x10000054,0x8000000,0x14,};
  final private JJCalls[] jj_2_rtns = new JJCalls[1];
  private boolean jj_rescan = false;
  private int jj_gc = 0;
  public Parser(java.io.InputStream stream) {
    jj_input_stream = new ASCII_CharStream(stream, 1, 1);
    token_source = new ParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 24; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  public void ReInit(java.io.InputStream stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 24; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  public Parser(java.io.Reader stream) {
    jj_input_stream = new ASCII_CharStream(stream, 1, 1);
    token_source = new ParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 24; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 24; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  public Parser(ParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 24; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  public void ReInit(ParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 24; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  final private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  final private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    return (jj_scanpos.kind != kind);
  }

  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

  final public Token getToken(int index) {
    Token t = lookingAhead ? jj_scanpos : token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  final private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.Vector<int[]> jj_expentries = new java.util.Vector<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;
  private int[] jj_lasttokens = new int[100];
  private int jj_endpos;

  private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      boolean exists = false;
      for (Enumeration<int[]> enu = jj_expentries.elements(); enu.hasMoreElements();) {
        int[] oldentry = (enu.nextElement());
        if (oldentry.length == jj_expentry.length) {
          exists = true;
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              exists = false;
              break;
            }
          }
          if (exists) break;
        }
      }
      if (!exists) jj_expentries.addElement(jj_expentry);
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  final public ParseException generateParseException() {
    jj_expentries.removeAllElements();
    boolean[] la1tokens = new boolean[30];
    for (int i = 0; i < 30; i++) {
      la1tokens[i] = false;
    }
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 24; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 30; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.addElement(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.elementAt(i);
    }
   return new ParseException(token, exptokseq, tokenImage);
  }

  final private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 1; i++) {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
          }
        }
        p = p.next;
      } while (p != null);
    }
    jj_rescan = false;
  }

  final private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }
}