import java.util.*
import kotlin.math.pow


const val TARGET_BAD_VERSION = 5


fun main() {
    val arrayL1 = arrayListOf(2, 4, 9)
    val arrayL2 = arrayListOf(5, 6, 4, 9)

    search(intArrayOf(-1, 0, 3, 5, 9, 12), 9)
    println(firstBadVersion(5000))
    println(lengthOfLongestSubString("checkString"))
    topKFrequent(intArrayOf(1, 5, 3, 5, 8, 8, 5), 5).print()

    addTwoNumbers(arrayToNodeList(arrayL1), arrayToNodeList(arrayL2))?.print()


    println(
        searchInsert(
            intArrayOf(1, 3, 5, 6), 5
        )
    )
}

fun sortedSquares(nums: IntArray): IntArray {
    val powMap = nums.map { num -> num.toDouble().pow(2.0).toInt() }

    return powMap.toIntArray().sortedArray()
}

fun rotateArray(nums: IntArray, k: Int): Unit {
    val numsCopy = nums.copyOf()
    for (i in nums.indices) {
        numsCopy[(i + k) % (nums.size)] = nums[i]

    }
    for (i in numsCopy.indices) {
        nums[i] = numsCopy[i]

    }
}


fun search(nums: IntArray, target: Int): Int {

    var start = 0
    var end = nums.size - 1

    while (start <= end) {
        val mid = start + (end - start) / 2
        if (nums[mid] == target)


            return mid
        if (target > nums[mid])
            start = mid + 1
        else
            end = mid - 1


    }
    return -1

}

fun searchInsert(nums: IntArray, target: Int): Int {
    var firstIndex = 0
    var lastIndex = nums.size - 1

    while (firstIndex <= lastIndex) {
        val midIndex = firstIndex + (lastIndex - firstIndex) / 2

        if (target > nums[midIndex]) {
            firstIndex = midIndex + 1


        } else {
            lastIndex = midIndex - 1

        }


    }

    return firstIndex
}

fun firstBadVersion(n: Int): Int {
// Initialize first index to look for
    var first = 0
// Initialize last index to look for
    var last = n
    while (first < last) {
// Calculate mid value
        val mid = first + (last - first) / 2
        if (isBadVersion(mid)) {
// We are sure that the bad version is located in the first part
// We update the last value to match the mid
            last = mid

        } else {
// We are sure that the bad version is located in the second part
// We update the first value to match the mid +1 since we are sure that the mid isn't a bad version hence it isn't the first bad version
            first = mid + 1

        }


    }
    // The loop will narrow the search area until first and last will be equal so we return their value

    return first

}

fun lengthOfLongestSubString(s: String): Int {

// pointer to track the beginning of a substring
    var i = 0
// pointer to iterate over a substring
    var j = 0
// a value to hold the length of the longest substring
    var max = 0

// A set that holds the characters of the substring being treated
    val hashSet: MutableSet<Char> = mutableSetOf()

// iterate over substring
    while (j < s.length) {
// check if the current character exists in the substring
        if (hashSet.contains(s[j])) {
// Advance i to start the next substring from the next position
            i++
// set j to match i
            j = i
// check if the length of the recorded substring is the max
            if (hashSet.size > max)
                max = hashSet.size
            hashSet.clear()
        } else {
            hashSet.add(s[j])
            j++
            if (hashSet.size > max)
                max = hashSet.size


        }

    }

    return max
}

fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
// Initializing the head that will be serving as the starting point to the node list that will be returned by the function
    val dummyHead = ListNode(0)

// A reference that will be updated each time to point to the last node
    var result: ListNode? = dummyHead

// A reference for l1 and l2 that will be updated each time to point to the next ListNode respectively for l1 and l2 (Kotlin won't allow function parameters to be var )
    var l1Copy = l1
    var l2Copy = l2

// The carry that will be saved on each addition operation to be later used for the next operation
    var carry = 0
// The loop will be executed as long as we have one non-null listNode or a non-null carry
    while (l1Copy != null || l2Copy != null || carry > 0) {
// Calculate the sum for every iteration of the values of each listNode alongside the carry
        val sum = carry + (l1Copy?.value ?: 0) + (l2Copy?.value ?: 0)
// Calculate the carry for the next operation
        carry = sum / 10
//Calculate the last digit that will be affected to the current result listNode
        val lastDigit = sum % 10

// Construct the next node list for the current result reference
        result?.next = ListNode(lastDigit)

// Update the result reference to point to the newly created next ListNode
        result = result?.next
// Update the l1Copy and l2Copy references respectively to point to the next ListNode
        l1Copy = l1Copy?.next
        l2Copy = l2Copy?.next
    }
// Return the next listNode of the dummy head as the first list node is useless and serves as initialization
    return dummyHead.next


}

fun topKFrequent(nums: IntArray, k: Int): IntArray {
    val frequencyHash = mutableMapOf<Int, Int>()
    nums.forEach { num ->
        frequencyHash[num] = (if (frequencyHash.containsKey(num)) frequencyHash[num]!! else 0) + 1
    }


    return frequencyHash.toList().sortedByDescending { (_, value) -> value }.toMap().keys.toIntArray()
        .sliceArray(0..if (k < frequencyHash.toList().size) k else frequencyHash.toList().size - 1)

}


// Helpers
fun IntArray.print() = this.forEach { item -> println(item) }
fun ListNode.print() {
    var l: ListNode? = this
    do {
        print(l?.value)
        l = l?.next

    } while (l?.next != null)

}

class ListNode(var value: Int) {
    var next: ListNode? = null
}


fun arrayToNodeList(arrayL1: ArrayList<Int>): ListNode? {

    val dummyHead = ListNode(0)
    var l = dummyHead
    arrayL1.forEach { number ->
        l.next = ListNode(number)
        l = l.next!!
    }
    return dummyHead.next
}

fun isBadVersion(i: Int): Boolean = i >= TARGET_BAD_VERSION


//NeetCode
//*Easy
fun containsDuplicate(nums: IntArray): Boolean = nums.toHashSet().size != nums.size
fun isAnagram(s: String, t: String): Boolean = s.toList().sorted() == t.toList().sorted()
fun twoSum(nums: IntArray, target: Int): IntArray {

    var i = 0
    var j = 0


    while (j < nums.size) {
        i++
        if (i > nums.size - 1) {
            j++
            i = j


        }

        if (nums[i] + nums[j] == target && i != j)
            return intArrayOf(i, j)


    }
    return intArrayOf()

}

//Two pointers
//*Easy
fun isPalindrome(s: String): Boolean {
    var newString = ""
    s.map {
        if (it.isLetter() || it.isDigit())
            newString += it
    }
    return newString.toUpperCase().reversed()==s.toUpperCase()

}