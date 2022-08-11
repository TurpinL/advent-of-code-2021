use std::fs;
use itertools::Itertools;
use itertools::izip;
use regex::Regex;

fn main() {
    println!("Part 1");
    part1();

    println!("\nPart 2");
    // part2();
}

fn part1() {
    let input_lines = include_str!("../input").lines();
    let threshold = (include_str!("../input").lines().count() / 2) as i32;

    let mut occurences_of_1: Vec<i32> = vec![0; 12];

    for line in input_lines {
        for (index, char) in line.chars().enumerate() {
            if char == '1' { occurences_of_1[index] += 1 }
        }
    }

    println!("{:?}", occurences_of_1);


    let epsilonString: String = occurences_of_1.iter().map(|x| if x > &threshold { '0' } else { '1' }).collect();
    let gammaString: String = occurences_of_1.iter().map(|x| if x > &threshold { '1' } else { '0' }).collect();

    let epsilon = isize::from_str_radix(&epsilonString, 2).unwrap();
    let gamma = isize::from_str_radix(&gammaString, 2).unwrap();

    println!("{} x {} = {}", epsilon, gamma, epsilon * gamma )
}

fn part2() {
    let input_lines = include_str!("../input").lines();
    let threshold = include_str!("../input").lines().count() / 2;

    let mut current_bit: usize = 0;

    let mut filtered_lines: Vec<&str> = input_lines.filter(|x| true).collect();

    while current_bit < 12 && filtered_lines.len() > 1 {
        let occurences_of_1 = filtered_lines.iter()
                .filter( |line| line.chars().nth(current_bit).unwrap() == '1' )
                .count();

        // Keep lines that start with the dominant value
        if occurences_of_1 >= threshold {
            filtered_lines.retain(|line| line.chars().nth(current_bit).unwrap() == '1');
        }
    }

    // for line in input_lines {
    //     for (index, char) in line.chars().enumerate() {
    //         if char == '1' { occurences_of_1[index] += 1 }
    //     }
    // }

    // println!("{:?}", occurences_of_1);


    // let epsilonString: String = occurences_of_1.iter().map(|x| if x > &threshold { '0' } else { '1' }).collect();
    // let gammaString: String = occurences_of_1.iter().map(|x| if x > &threshold { '1' } else { '0' }).collect();

    // let epsilon = isize::from_str_radix(&epsilonString, 2).unwrap();
    // let gamma = isize::from_str_radix(&gammaString, 2).unwrap();

    // println!("{} x {} = {}", epsilon, gamma, epsilon * gamma )
}