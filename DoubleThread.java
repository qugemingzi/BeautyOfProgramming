public class DoubleThread {
    /*
        下载问题高效化，从网络上下载数据，然后存储到硬盘上
        一个简单的做法，下载一块数据，写入硬盘，然后再下载，再写入硬盘。。。如何优化？
        首先进行抽象和简化。
        1、假设所有的数据块的大小都是固定的，你可以使用一个全局缓冲区：
            Block g_buffer[BUFFER_COUNT]
        2、假设两个函数已经实现（函数正常工作，不会抛出异常）
        ***************************************************************************
        *    // downloads a block from Internet sequentially in each call
        *    // return true, if the entire file is downloaded, otherwise false.
        *    bool GetBlockFromNet(Block * out_block);
        *
        *    // writes a block to hard disk
        *    bool WriteBlockToDisk(Block * in_block);
        ***************************************************************************
        idea 1:
        串行方案，下载完一块数据之后才能写入硬盘。伪代码：
        ***************************************************************************
        * while(true){
        *     bool isDownloadCompleted;
        *     isDownloadCompleted = GetBlockFromNet(g_buffer);
        *     WriteBlockToDisk(g_buffer);
        *     if(isDownloadCompleted)
        *         break;
        * }
        ***************************************************************************
        idea 2:
        并行方案，设计两个线程
        线程A: 从网络中读取一个数据块，存储到内存的缓存中。
        线程B: 从缓冲中读取内容，存储到文件中。
        你可以使用下面的多线程API
        ***************************************************************************
        * class Thread{
        * public:
        *     // initialize a thread and set the work function
        *     Thread(void (*work_func)());
        *     // once the object is destructed, the thread will be aborted
        *     ~Thread();
        *     // start the thread
        *     void Start();
        *     // stop the thread
        *     void Abort();
        * };
        *
        * class Semaphore{
        * public:
        *     // initialize semaphore counts
        *     Semaphore(int count, int max_count);
        *     ~Semaphore();
        *     // consume a signal (count--), block current thread if count == 0
        *     void Unsignal();
        *     // raise a signal (count++)
        *     void Signal();
        * };
        *
        * class Mutex{
        * public:
        *     // block thread until other threads release the mutex
        *     WaitMutex();
        *     // release mutex to let other thread wait fot it
        *     ReleaseMutex();
        * };
        ***************************************************************************
        如果网络延迟为L1，磁盘I/O延迟为L2，对比多线程与单线程，分析性能和改进的设计方法
        analysis:
        需要协调两个线程的工作，考虑如下
        1、什么时候才算完成任务？
            两个线程必须协同工作，将网络上的数据下载完毕并且完全存储到硬盘上，只有在这个
            时候，两个线程才能正常终止。
        2、为了提高效率，希望两个线程就能尽可能地同时工作。
            如果使用Mutex，下载和存储线程将不能同时工作。因此，Semaphore是更好的选择。
        3、下载和存储线程工作的必要条件如下。
            如果共享缓冲区已满，没有缓冲空间来存储下载的内同，则应该暂停下载。如果所有的
            内容都已经下载完毕，也没必要继续下载。
            如果缓冲区为空，则没必要运行存储线程。进一步，如果下载工作已经完成，存储线程
            也可以结束了。
        4、共享缓冲区的数据结构。
            下载线程和存储线程工作的过程时“先进先出”，先下载的内容要先存储，这样才能保证
            内容的正确顺序。“先进先出”的典型数据结构是队列。由于我们采用了固定的缓冲空间
            来保存下载的内容，循环队列是一个很好的选择。
        综合以上伪代码如下：
        ***************************************************************************
        * #define BUFFER_COUNT 100
        * Block g_buffer[BUFFER_COUNT];
        *
        * Thread g_threadA(ProcA);
        * Thread g_threadB(ProcB);
        * Semaphore g_seFull(0, BUFFER_COUNT);
        * Semaphore g_seEmpty(BUFFER_COUNT, BUFFER_COUNT);
        * bool g_downloadComplete;
        * int in_index = 0;
        * int out_index = 0;
        *
        * void main(){
        *   g_downloadCompleted = false;
        *   threadA.Start();
        *   threadB.Start();
        *   // wait here until threads finished
        * }
        *
        * void ProcA(){
        *   while(true){
        *       g_seEmpty.Unsignal();
        *       g_downloadComplete = GetBlockFromNet(g_buffer + in_index);
        *       index = (in_index + 1) % BUFFER_COUNT;
        *       g_seFull.Signal();
        *       if(g_downloadComplete)
        *           break;
        *   }
        * }
        *
        * void ProcB(){
        *   while(true){
        *       g_seFull.Unsignal();
        *       WriteBlockToDisk(g_buffer + out_index);
        *       out_index = (out_index + 1) % BUFFER_COUNT;
        *       g_seEmpty.Signal();
        *       if(g_downloadComplete && out_index == in_index)
        *           break;
        *   }
        * }
        ***************************************************************************
        上述伪代码中，ProcA和ProcB的操作能够一一对应起来，下载线程和存储线程可以同时工作
        看起来很美。如果网络延迟为L1，磁盘I/O为L2，那么这个多线程程序执行时间约为Max(L1, L2)
        尤其是在需要下载的内容很多的时候，基本可以保证两个线程是流水线工作。单线程情况下
        需要的时间是L1+L2。
        extension:
        如果网络延时远大于I/O存储延迟，多线程的设计如何进一步改善性能？
        story:
        问题源自Outlook打开时需要下载“离线地址簿文件”(OAB, Offline Address Book)，比较大
        之前采用单线程进度缓慢，之后采取双线性快多了，而且检测用户正在使用电脑就放慢速度，比
        如检测用户在几秒内没有鼠标、键盘的输入，则双线程会回复高速运行。
        也许提高程序性能(performance)的最高境界，就是把事情做了，同时有不让用户感觉到程序在
        费力做事情。
     */
}
