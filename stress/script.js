import http from 'k6/http';
import { check, sleep } from 'k6';

// k6 load options
export const options = {
  vus: 20,          // virtual users
  duration: '30s',  // total duration
};

const accountNumbers = ['123123', '787878'];

// Helper: pick random element
function randomChoice(arr) {
  return arr[Math.floor(Math.random() * arr.length)];
}

// Helper: random float with 2 decimals
function randomAmount(min = 1, max = 10) {
  return (Math.random() * (max - min) + min).toFixed(2);
}

export default function () {
  const account = randomChoice(accountNumbers);

  // -------- 1) GET account details --------
  let res = http.get(`http://localhost:8080/account/${account}`);
  check(res, { 'GET status 200': (r) => r.status === 200 });

  // -------- 2) POST deposit --------
  // const depositPayload = JSON.stringify({
  //   accountNumber: account,
  //   clientId: "1",
  //   amount: randomAmount(),
  // });

  const withdrawnlPayload = JSON.stringify({
    accountNumber: account,
    clientId: "1",
    amount: randomAmount(),
  });


  res = http.post('http://localhost:8080/account/withdrawal', withdrawnlPayload, {
    headers: { 'Content-Type': 'application/json' },
  });
  check(res, { 'POST withdrawal status 200': (r) => r.status === 200 });

  // res = http.post('http://localhost:8080/account/deposit', depositPayload, {
  //   headers: { 'Content-Type': 'application/json' },
  // });
  // check(res, { 'POST deposit status 200': (r) => r.status === 200 });


  // -------- 3) POST transaction (transfer) --------
  const source = randomChoice(accountNumbers);
  let target = randomChoice(accountNumbers);

  // Make sure source and target are not equal
  while (target === source) {
    target = randomChoice(accountNumbers);
  }

  const transactionPayload = JSON.stringify({
    sourceAccount: source,
    targetAccount: target,
    amount: randomAmount(),
  });

  res = http.post('http://localhost:8080/account', transactionPayload, {
    headers: { 'Content-Type': 'application/json' },
  });
  check(res, { 'POST transaction status 200': (r) => r.status === 200 });

  // Pause to simulate user think time
  sleep(1);
}
