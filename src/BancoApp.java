class SaldoInsuficienteException extends Exception {
    public SaldoInsuficienteException(String mensaje) {
        super(mensaje);
    }
}

class MontoNegativoException extends Exception {
    public MontoNegativoException(String mensaje) {
        super(mensaje);
    }
}

class CuentaBancaria {
    private String titular;
    private double saldo;

    public CuentaBancaria(String titular, double saldoInicial) {
        this.titular = titular;
        this.saldo = saldoInicial;
    }

    public void retirar(double monto) throws SaldoInsuficienteException {
        if (monto > saldo) {
            throw new SaldoInsuficienteException("Saldo insuficiente para retirar " + monto);
        }
        saldo -= monto;
        System.out.println("Retiro de " + monto + " realizado. Saldo actual: " + saldo);
    }

    public void depositar(double monto) throws MontoNegativoException {
        if (monto < 0) {
            throw new MontoNegativoException("El monto a depositar no puede ser negativo.");
        }
        saldo += monto;
        System.out.println("Depósito de " + monto + " realizado. Saldo actual: " + saldo);
    }

    public void transferir(CuentaBancaria cuentaDestino, double monto) throws SaldoInsuficienteException, MontoNegativoException {
        if (monto < 0) {
            throw new MontoNegativoException("El monto a transferir no puede ser negativo.");
        }
        retirar(monto);
        cuentaDestino.depositar(monto);
        System.out.println("Transferencia de " + monto + " a " + cuentaDestino.getTitular() + " realizada.");
    }

    public String getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }
}

public class BancoApp {
    public static void main(String[] args) {
        CuentaBancaria cuenta1 = new CuentaBancaria("Alice", 500);
        CuentaBancaria cuenta2 = new CuentaBancaria("Bob", 300);

        try {
            cuenta1.depositar(200); // Depósito válido
            cuenta1.retirar(100);    // Retiro válido
            cuenta1.transferir(cuenta2, 150); // Transferencia válida

            // Intento de retirar más de lo que hay en la cuenta
            cuenta1.retirar(700);
        } catch (SaldoInsuficienteException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (MontoNegativoException e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            // Intento de depósito negativo
            cuenta2.depositar(-50);
        } catch (MontoNegativoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
