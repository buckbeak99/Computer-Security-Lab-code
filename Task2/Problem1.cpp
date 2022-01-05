// input: krclxrwrbxwnxocqnlxxunbcrwencrxwbrwanlnwccrvnb

#include<bits/stdc++.h>
using namespace std;

int main()
{
    char cipherText[100], ch;
    int i, shiftKey, operation;

    cout << "Enter a Encrypted or Decrypted message: ";
    cin.getline(cipherText,100);
    cout << "Enter Shifting Key: ";
    cin >> shiftKey;

    cout<< "Choose Operation: \n";
    cout << "1. Encryption\n" << "2. Decryption\n";
    cin>>operation;

    switch (operation)
    {
    case 1:
        for(i=0; cipherText[i]!='\0';i++);{
            ch = cipherText[i];
            // chech the message alphabets in plain alphabet table
            if(ch >='a'&& ch<='z'){
                // shift right the alphabet according to given shiftkey
                ch = ch+ shiftKey;
                // now replace plain alphabet with caesar cipher alphabet
                // for example plain text: DCODEX, shift key = 3
                // now shift right 3 alphabet, get D = G, C = F
                // O = R, E = H, X = A
                // DCODEX(original text) = GFRGHA(cipher text)
                if(ch>'z'){
                    ch = ch -'z' + 'a' - 1;
                }
                cipherText[i]= ch;
            }
            else if(ch >='A'&& ch<='Z'){
                ch = ch+ shiftKey;
                if(ch>'Z'){
                    ch = ch -'Z' + 'A' - 1;
                }
                cipherText[i]= ch;
            }
        }

        cout << "Encrpted Message: \n" << cipherText;
        break;
    case 2:
        for(i = 0; cipherText[i] != '\0'; ++i){
		    ch = cipherText[i];
            // chech the message alphabets in ceaser cipher alphabet table
            if(ch >= 'a' && ch <= 'z'){
                // shift left the alphabets according to given shiftkey
                ch = ch - shiftKey;
                 // now replace ceaser cipher alphabet with plain alphabet
                // for example cipher text: GFRGHA, shift key = 3
                // now shift left 3 alphabet, get G = D, F = C
                // R = O, H = E, A = x
                // GFRGHA(cipher text)= DCODEX(original text)
                if(ch < 'a'){
                    ch = ch + 'z' - 'a' + 1;
                }
                
                cipherText[i] = ch;
            }
            else if(ch >= 'A' && ch <= 'Z'){
                ch = ch - shiftKey;
                
                if(ch > 'a'){
                    ch = ch + 'Z' - 'A' + 1;
                }
                
                cipherText[i] = ch;
            }
        }
        
        cout << "Decrypted cipherText: \n" << cipherText <<"\n";
        break;
    default:
         printf("\nError\n");
        break;
    }
    return 0;

}