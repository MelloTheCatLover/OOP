class Heap{
    int array[];
    int len;
    public Heap(int arr[]){
        array = arr;
        len = arr.length;

        buildHeap();
    }

    void buildHeap(){
        for(int i = len/2 - 1; i >= 0; i--){
            heapify(i);
        }
    }

    void heapify(int root_index){
        int child = root_index * 2 + 1; //left side
        int max_index = root_index;
        if (child < len){
            if (array[child] > array[root_index]){
                max_index = child;
            }
        }

        if(child + 1< len){
            if(array[child+1] > array[max_index]){
                max_index = child+1;
            }
        }

        swap(array, root_index, max_index);
        if(root_index != max_index){
            heapify(max_index);
        }
    }

    void swap(int arr[], int a, int b){
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    int extractRoot(){
        int max = array[0];
        array[0] = array[--len];
        heapify(0);

        return max;
    }

    int  getSize(){
        return len;
    }

}

class Main{
    public static void main(String args[]){
        int arr[] = {1, 2, 4, 7, 6, 5, 88, 11, 22, 76};
        Heap h = new Heap(arr);

        while(h.getSize() > 0){
            System.out.print(h.extractRoot()+", ");
        }
    }

}
