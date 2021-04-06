#include<stdio.h>
#include<stdlib.h>
#include<malloc.h>

struct tree{
    int value;
    struct tree* left;
    struct tree* right;
};
typedef struct tree Tree;

Tree* initialize(){
    return NULL;
}

Tree* add(Tree* nod, int number) {
    if (nod == NULL) {
        nod = (Tree*) malloc(sizeof (Tree));
        if (nod == NULL) {
            return NULL;
        }
        nod->value = number;
        nod->left = NULL;
        nod->right = NULL;
        return nod;
    }
    if (nod->value > number) {
        nod->left = add(nod->left, number);
    } else {
        nod->right = add(nod->right, number);
    }
    return nod;
}

Tree * findMinimum(Tree *t) {
    if (t == NULL) {
        return t;
    }
    if (t->left == NULL) {
        return t;
    }
    return findMinimum(t->left);
}

Tree* pop(Tree* t, int x) {
    Tree* tmp;
    if (t == NULL) {
        return t;
    }
    if (x < t->value) {
        t->left = pop(t->left, x);
    } else {
        if (x > t->value) {
            t->right = pop(t->right, x);
        } else { // here we say that the searched node is found
            if (t->left && t->right) {
                tmp = findMinimum(t->right);
                t->value = tmp->value;
                t->right = pop(t->right, t->value);
            } else {
                tmp = t;
                if (t->left == NULL) {
                    t = t->right;
                } else {
                    if (t->right == NULL) {
                        t = t->left;
                    }
                    free(tmp);
                }
            }
        }
    }
    return t;
}

Tree* create(int toPush){
    Tree* newNode = (Tree*) malloc(sizeof(Tree*));
    newNode->value = toPush;
    newNode->left = NULL;
    newNode->right = NULL;
    return newNode;
}

void showTree(Tree* tree){
    if (tree == NULL){
        return;
    }
    printf("%d", tree->value); /* mostra raiz */
    printf("(");
    showTree(tree->left); /* mostra sae */
    showTree(tree->right); /* mostra sad */
    printf(")");

}

int numberOfSons(Tree *root) {
    if (root == NULL) {
        return 0;
    }
    return 1 + numberOfSons(root->left) + numberOfSons(root->right);
}

int rank(Tree* root) {
    int left, right;
    if (root == NULL) {
        return -1;
    }
    left = rank(root->left);
    right = rank(root->right);
    return 1 + (left > right ? left : right);
}

Tree * find(Tree * nod, int val) {
    if (nod == NULL) {
        return NULL;
    }
    if (nod->value == val) {
        return nod;
    }
    if (nod->value > val) {
        return find(nod->left, val);
    }
    return find(nod->right, val);
}

void printInorder(Tree* nod) {
    if (nod == NULL) {
        return;
    }
    printInorder(nod->left);
    printf(" %d ", nod->value);
    printInorder(nod->right);
}


void main(){
    Tree* tree;
    tree = initialize();
    printf("\ndei o primeiro push no 5\n");
    tree = add(tree, 5);
    printf("\ndei o primeiro push no 3\n");
    tree = add(tree, 7);
    printf("\ndei o primeiro push no 4\n");
    tree = add(tree, 6);
    printf("\ndei o primeiro push no 1\n");
    tree = add(tree, 1);
    showTree(tree);
    tree = pop(tree,7);
    printf("\n\n");
    showTree(tree);
}
